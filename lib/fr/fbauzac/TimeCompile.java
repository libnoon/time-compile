package fr.fbauzac;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TimeCompile {

    public static void summarize(String maps, List<String> lines) throws TimeCompileException {
	TagTransformer tagTransformer = new IdentityTagTransformer();
	if (maps != null) {
	    for (String fileName : maps.split(",")) {
		Path path = Paths.get(fileName);
		tagTransformer = new FileTagTransformer(path, tagTransformer);
	    }
	}

	TimeCompile.processLines(lines, tagTransformer);
    }

    private static void processLines(List<String> lines, TagTransformer tagTransformer) throws TimeCompileException {
	IntervalsReader reader = new IntervalsReader();
	List<Interval> intervals = reader.convert(lines);

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
		System.out.format("%15s  %7s (%.0f%%)%n", tag.toString(), duration, percent);
	    }
	}
	System.out.format("%15s  %7s%n", "TOTAL", new Duration(totalMinutes));
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
