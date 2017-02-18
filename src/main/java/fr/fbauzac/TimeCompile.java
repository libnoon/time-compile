package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TimeCompile {

    public static void main(String[] args) throws TimeCompileException {
	List<String> lines;
	try {
	    lines = Files.readAllLines(Paths.get(args[0]));
	} catch (IOException e) {
	    System.out.format("cannot read lines from %s%n", args[0]);
	    String str = String.format("cannot read lines from %s%n", args[0]);
	    throw new TimeCompileException(str, e);
	}

	TagTransformer tagTransformer = new IdentityTagTransformer();
	if (args.length >= 2) {
	    for (String fileName : args[1].split(",")) {
		Path path = Paths.get(fileName);
		tagTransformer = new FileTagTransformer(path, tagTransformer);
	    }
	}

	System.err.println("tagTransformer: " + tagTransformer);

	TimeCompile.processLines(lines, tagTransformer);
    }

    private static void processLines(List<String> lines, TagTransformer tagTransformer) throws TimeCompileException {
	IntervalsReader reader = new IntervalsReader();
	List<Interval> intervals = reader.convert(lines);
	for (Interval interval : intervals) {
	    System.out.println("Interval:");
	    interval.print(System.out, "  ");
	    for (Tag tag : interval.getTags()) {
		System.out.println("Tag: " + tag);
	    }
	    System.out.println("Duration: " + interval.getDuration());
	    System.out.println();
	}

	Map<Tag, Category> categoriesMap = new HashMap<>();
	for (Interval interval : intervals) {
	    List<Tag> tags = interval.getTags();
	    if (tags.size() == 0) {
		// Nothing to record.
	    } else if (tags.size() == 1) {
		Tag tag = tagTransformer.transform(tags.get(0));
		if (tag.toString().equals("")) {
		    // Skip.
		} else {
		    Category tagInfo = ensureCategory(categoriesMap, tag);
		    tagInfo.addInterval(interval);
		}
	    } else {
		System.err.println("Ignoring multitag interval " + interval);
	    }
	}

	int totalMinutes = 0;
	for (Category category : categoriesMap.values()) {
	    totalMinutes += category.getDuration().getMinutes();
	}

	List<Tag> tags = new ArrayList<>();
	tags.addAll(categoriesMap.keySet());
	Collections.sort(tags);
	for (Tag tag : tags) {
	    if (tag.toString().equals("")) {
		// Ignored.
	    } else {
		Category category = categoriesMap.get(tag);
		Duration duration = category.getDuration();
		int durationMinutes = duration.getMinutes();
		double percent = 100.0 * durationMinutes / totalMinutes;
		System.out.format("%10s  %7s (%.0f%%)%n", tag.toString(), duration, percent);
	    }
	}
	System.out.format("%10s  %7s%n", "TOTAL", new Duration(totalMinutes));
    }

    private static Category ensureCategory(Map<Tag, Category> tagInfos, Tag tag) {
	if (tagInfos.containsKey(tag)) {
	    // Nothing to do.
	} else {
	    tagInfos.put(tag, new Category());
	}
	return tagInfos.get(tag);
    }

}
