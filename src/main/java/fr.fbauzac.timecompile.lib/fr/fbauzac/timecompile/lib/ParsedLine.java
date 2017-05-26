package fr.fbauzac.timecompile.lib;

import java.util.List;

public interface ParsedLine {

    String getLine();

    void update(IntervalBuilder builder, List<Interval> output) throws TimeCompileException;

}
