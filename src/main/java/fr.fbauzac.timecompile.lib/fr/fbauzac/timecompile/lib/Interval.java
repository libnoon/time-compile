package fr.fbauzac.timecompile.lib;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * An interval of text between two timestamps, e.g.:
 *
 * <pre>
 * 12h00
 * +lunch at the GK
 * 13h45
 * </pre>
 */
public class Interval {

    /**
     * The timestamp of the start of the interval.
     */
    private TimeParsedLine startLine;

    /**
     * The timestamp of the end of the interval.
     */
    private TimeParsedLine endLine;

    /**
     * The sequence of content lines in the Interval.
     */
    private List<ContentParsedLine> lines;

    public Interval(TimeParsedLine startLine, TimeParsedLine endLine, List<ContentParsedLine> lines) {
	this.startLine = startLine;
	this.endLine = endLine;
	this.lines = lines;
    }

    private static final Pattern TAG_PATTERN = Pattern.compile("(?:\\A| |\n)\\+([\\p{Alnum}-]+)");

    static List<String> getTags(String contents) {
	List<String> accu = new ArrayList<>();
	Matcher matcher = TAG_PATTERN.matcher(contents);
	while (matcher.find()) {
	    accu.add(matcher.group(1));
	}
	return accu;
    }

    public List<String> getTags() {
	String contents = String.join("\n",
		lines.stream().map(ContentParsedLine::getLine).collect(Collectors.toList()));
	return getTags(contents);
    }

    private void printWithPrefix(PrintStream out, String prefix, String line) {
	out.print(prefix);
	out.println(line);
    }

    public void print(PrintStream out, String prefix) {
	printWithPrefix(out, prefix, startLine.getLine());
	lines.stream().forEach(line -> printWithPrefix(out, prefix, line.getLine()));
	printWithPrefix(out, prefix, endLine.getLine());
    }

    public Duration getDuration() {
	return startLine.getTime().durationTo(endLine.getTime());
    }

}
