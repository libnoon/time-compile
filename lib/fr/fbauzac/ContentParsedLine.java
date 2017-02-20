package fr.fbauzac;

import java.util.List;

public final class ContentParsedLine implements ParsedLine {

    private String line;

    public String getLine() {
	return line;
    }

    public void setLine(String line) {
	this.line = line;
    }

    public ContentParsedLine(String line) {
	this.line = line;
    }

    @Override
    public void update(IntervalBuilder builder, List<Interval> output) {
	builder.addLine(this);
    }

}
