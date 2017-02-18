package fr.fbauzac;

import java.util.List;

public final class TagInfo {

    private List<Interval> intervals;

    public void addInterval(Interval interval) {
	intervals.add(interval);
    }

}
