package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    }

}
