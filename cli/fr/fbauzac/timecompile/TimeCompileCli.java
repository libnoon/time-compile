package fr.fbauzac.timecompile;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

final class TimeCompileCli {

    public static void main(String[] args) throws TimeCompileException {
	OptionParser parser = new OptionParser();

	ArgumentAcceptingOptionSpec<String> mapsLongOption = parser.accepts("map").withRequiredArg();
	ArgumentAcceptingOptionSpec<String> mapsShortOption = parser.accepts("m").withRequiredArg();
	parser.accepts("help");
	parser.accepts("h");

	OptionSet options = parser.parse(args);
	if (options.has("h") || options.has("help")) {
	    usage();
	    return;
	}
	if (options.nonOptionArguments().size() < 1) {
	    System.err.println("Missing parameter");
	    System.exit(1);
	} else if (options.nonOptionArguments().size() > 1) {
	    System.err.println("Too many arguments");
	    System.exit(1);
	}

	List<String> maps = new ArrayList<>();
	maps.addAll(options.valuesOf(mapsLongOption));
	maps.addAll(options.valuesOf(mapsShortOption));

	String commandLineFileName = (String) options.nonOptionArguments().get(0);

	List<String> lines;
	if (commandLineFileName.equals("-")) {
	    try (InputStreamReader isr = new InputStreamReader(System.in);
		    BufferedReader br = new BufferedReader(isr);) {
		lines = br.lines().collect(toList());
	    } catch (IOException e) {
		throw new TimeCompileException("cannot read lines from stdin", e);
	    }
	} else {
	    try {
		lines = Files.readAllLines(Paths.get(commandLineFileName));
	    } catch (IOException e) {
		String str = String.format("cannot read lines from %s", args[0]);
		throw new TimeCompileException(str, e);
	    }
	}

	Map<String, String> map = new HashMap<String, String>();
	for (String mapFile : maps) {
	    Map<String, String> fileMap = parseMapFile(Paths.get(mapFile));
	    map.putAll(fileMap);
	}

	Summary summary = TimeCompile.summarize(lines, map);

	for (Category category : summary.getCategories()) {
	    String tag = category.getTag();
	    Duration duration = category.getDuration();
	    int durationMinutes = duration.getMinutes();
	    double percent = 100.0 * durationMinutes / summary.getTotalDuration().getMinutes();
	    System.out.format("%15s  %7s (%.0f%%)%n", tag, duration, percent);
	}
	System.out.format("%15s  %7s%n", "TOTAL", summary.getTotalDuration());

    }

    private static Map<String, String> parseMapFile(Path path) throws TimeCompileException {
	List<String> lines;
	try {
	    lines = Files.readAllLines(path);
	} catch (IOException e) {
	    String message = String.format("cannot read %s%n", path);
	    throw new TimeCompileException(message, e);
	}
	return MapParser.parse(lines);
    }

    private static void usage() {
	System.err.println("time-compile [OPTION...] TIMEFILE");
	System.err.println(" Options:");
	System.err.println("   -h, --help          Show this help screen.");
	System.err.println("   -m, --map=MAPFILE   Apply transformations to the tags.");
	System.err.println("                       This option can be repeated.");
    }
}
