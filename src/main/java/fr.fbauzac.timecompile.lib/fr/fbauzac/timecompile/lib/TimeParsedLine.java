package fr.fbauzac.timecompile.lib;

import java.util.List;

public final class TimeParsedLine implements ParsedLine {

    private String line;
    private Time time;

    public TimeParsedLine(String line, String hour, String min) {
	this.line = line;
	this.time = new Time(Integer.valueOf(hour), Integer.valueOf(min));
    }

    @Override
    public void update(IntervalBuilder builder, List<Interval> output) throws TimeCompileException {
	if (builder.hasStartTime()) {
	    builder.setEnd(this);
	    output.add(builder.buildAndReset());
	    builder.setStart(this);
	} else {
	    builder.setStart(this);
	}
    }

    public Time getTime() {
	return time;
    }

    @Override
    public String getLine() {
	return line;
    }

}
