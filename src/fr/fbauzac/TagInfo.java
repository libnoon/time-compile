package fr.fbauzac;

import java.util.ArrayList;
import java.util.List;

public final class TagInfo {

    private List<Interval> intervals = new ArrayList<>();

    public void addInterval(Interval interval) {
	intervals.add(interval);
    }

    public int durationMinutes() {
	return intervals.stream().mapToInt(Interval::durationMinutes).sum();
    }

}
