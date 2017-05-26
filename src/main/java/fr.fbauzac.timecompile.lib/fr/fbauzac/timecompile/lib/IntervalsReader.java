package fr.fbauzac.timecompile.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Read a list of lines and build a list of Interval.
 * 
 * @author noon
 *
 */
public final class IntervalsReader {

    public List<Interval> convert(List<String> lines) throws TimeCompileException {
	List<Interval> output = new ArrayList<>();
	IntervalBuilder builder = new IntervalBuilder();
	List<ParsedLine> parsedLines = lines.stream().map(ParsedLines::parse).collect(Collectors.toList());
	for (ParsedLine parsedLine : parsedLines) {
	    parsedLine.update(builder, output);
	}
	return output;
    }

}
