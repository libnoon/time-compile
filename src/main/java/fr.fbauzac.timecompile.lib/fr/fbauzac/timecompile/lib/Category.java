package fr.fbauzac.timecompile.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregated information about the occurrences of a tag.
 */
public final class Category {

    /**
     * Sequence of Intervals that fall into this Category.
     */
    private final List<Interval> intervals = new ArrayList<>();

    /**
     * Tag name of this Category.
     */
    private final String tag;

    /**
     * Build a new Category from a tag.
     *
     * <p>This Category is ready to be enriched with the appropriate
     * Intervals via the addInterval() method.
     *
     * @param tag the tag name for this Category.
     */
    public Category(String tag) {
	this.tag = tag;
    }

    /**
     * Add an interval to this Category.
     *
     * @param interval the Interval to add.
     */
    public void addInterval(Interval interval) {
	intervals.add(interval);
    }

    /**
     * Compute the sum of minutes of all Intervals of this Category.
     *
     * @return the duration of this Category, as a count of minutes.
     */
    private int durationMinutes() {
	return intervals.stream().map(Interval::getDuration).mapToInt(Duration::getMinutes).sum();
    }

    /**
     * Return the total duration of this Category.
     *
     * <p>This is the accumulated duration of all the Intervals in
     * this Category.
     *
     * @return the Duration of the Category.
     */
    public Duration getDuration() {
	return Duration.ofMinutes(durationMinutes());
    }

    /**
     * Get the tag of this Category.
     *
     * @return the tag, as a String.
     */
    public String getTag() {
	return tag;
    }

}
