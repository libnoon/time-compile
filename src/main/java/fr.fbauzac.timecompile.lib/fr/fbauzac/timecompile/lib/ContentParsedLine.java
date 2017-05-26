package fr.fbauzac.timecompile.lib;

import java.util.List;

/**
 * A parsed line with normal textual content, as opposed to a
 * timestamp.
 */
public final class ContentParsedLine implements ParsedLine {

    /**
     * The line, as a String.
     */
    private String line;

    /**
     * Return the line as a String.
     *
     * @return the line.
     */
    public String getLine() {
	return line;
    }

    /**
     * Set the line string.
     *
     * @param line the line string.
     */
    public void setLine(String line) {
	this.line = line;
    }

    /**
     * Build a ContentParsedLine from a line string.
     *
     * @param line the line string.
     */
    public ContentParsedLine(String line) {
	this.line = line;
    }

    @Override
    public void update(IntervalBuilder builder, List<Interval> output) {
	if (builder.hasStartTime()) {
	    builder.addLine(this);
	}
    }

}
