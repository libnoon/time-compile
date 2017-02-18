package fr.fbauzac;

import java.util.ArrayList;
import java.util.List;

public final class Category {

    private List<Interval> intervals = new ArrayList<>();

    public void addInterval(Interval interval) {
	intervals.add(interval);
    }

    private int durationMinutes() {
	return intervals.stream().map(Interval::getDuration).mapToInt(Duration::getMinutes).sum();
    }

    public Duration getDuration() {
	return new Duration(durationMinutes());
    }

}
