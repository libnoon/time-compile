package fr.fbauzac.timecompile;

import java.util.ArrayList;
import java.util.List;

public final class Category {

    private final List<Interval> intervals = new ArrayList<>();
    private final String tag;

    public Category(String tag) {
	this.tag = tag;
    }

    public void addInterval(Interval interval) {
	intervals.add(interval);
    }

    private int durationMinutes() {
	return intervals.stream().map(Interval::getDuration).mapToInt(Duration::getMinutes).sum();
    }

    public Duration getDuration() {
	return Duration.ofMinutes(durationMinutes());
    }

    public String getTag() {
	return tag;
    }

}
