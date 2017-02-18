package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	    System.out.println("Duration: " + interval.durationMinutes());
	    System.out.println();
	}

	Map<Tag, TagInfo> tagInfos = new HashMap<>();
	intervals.stream().forEach(interval -> {
	    List<Tag> tags = interval.getTags();
	    if (tags.size() == 0) {
		// Nothing to record.
	    } else if (tags.size() == 1) {
		Tag tag = tags.get(0);
		TagInfo tagInfo = ensureTagInfo(tagInfos, tag);
		tagInfo.addInterval(interval);
	    } else {
		System.err.println("Ignoring multitag interval " + interval);
	    }
	});

	for (Tag tag : tagInfos.keySet()) {

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
