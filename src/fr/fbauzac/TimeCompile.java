package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
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

	TimeCompile.processLines(lines);
    }

    private static void processLines(List<String> lines) throws TimeCompileException {
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

	Map<Tag, TagInfo> tagInfos = new HashMap<>();
	int totalMinutes = 0;
	for (Interval interval : intervals) {
	    List<Tag> tags = interval.getTags();
	    if (tags.size() == 0) {
		// Nothing to record.
	    } else if (tags.size() == 1) {
		Tag tag = tags.get(0);
		TagInfo tagInfo = ensureTagInfo(tagInfos, tag);
		tagInfo.addInterval(interval);
		totalMinutes += interval.getDuration().getMinutes();
	    } else {
		System.err.println("Ignoring multitag interval " + interval);
	    }
	}

	List<Tag> tags = new ArrayList<>();
	tags.addAll(tagInfos.keySet());
	Collections.sort(tags);
	for (Tag tag : tags) {
	    TagInfo tagInfo = tagInfos.get(tag);
	    int duration = tagInfo.durationMinutes();
	    double percent = 100.0 * duration / totalMinutes;
	    System.out.format("%s  %d (%.0f%%)%n", tag.toString(), duration, percent);
	}
    }

    private static TagInfo ensureTagInfo(Map<Tag, TagInfo> tagInfos, Tag tag) {
	if (tagInfos.containsKey(tag)) {
	    // Nothing to do.
	} else {
	    tagInfos.put(tag, new TagInfo());
	}
	return tagInfos.get(tag);
    }

}
