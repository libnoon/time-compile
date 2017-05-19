package fr.fbauzac.timecompile;

import java.util.ArrayList;
import java.util.List;

public final class IntervalBuilder {

    private TimeParsedLine startLine;
    private List<ContentParsedLine> lines;
    private TimeParsedLine endLine;

    public IntervalBuilder() {
	reset();
    }

    private void reset() {
	startLine = null;
	endLine = null;
	lines = new ArrayList<>();
    }

    public Interval buildAndReset() throws TimeCompileException {
	try {
	    return new Interval(startLine, endLine, lines);
	} finally {
	    reset();
	}
    }

    @Override
    public String toString() {
	if (lines.isEmpty()) {
	    return String.format("Builder(start=%s, end=%s)", startLine.getTime(), endLine.getTime());
	} else {
	    return String.format("Builder(start=%s, end=%s, {%s...}", startLine.getTime(), endLine.getTime(),
		    lines.get(0));
	}
    }

    public boolean hasStartTime() {
	return startLine != null;
    }

    public void setStart(TimeParsedLine timeParsedLine) {
	startLine = timeParsedLine;
    }

    public void addLine(ContentParsedLine line) {
	lines.add(line);
    }

    public void setEnd(TimeParsedLine line) throws TimeCompileException {
	if (endLine == null) {
	    endLine = line;
	} else {
	    String message = String.format("cannot setEnd(%s): current interval (%s) already has end %s", line,
		    toString(), endLine.getTime());
	    throw new TimeCompileException(message);
	}
    }

}