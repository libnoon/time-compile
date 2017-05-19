package fr.fbauzac.timecompile;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class TimeCompile {

    /**
     * Compute the summary of time spent, given the timeline and the mapping.
     * 
     * @param lines
     *            list of lines representing the timeline.
     * @param map
     *            the mapping for transformations of tags.
     * @return the summary.
     * @throws TimeCompileException
     *             when there is an error while processing the input.
     */
    public static Summary summarize(List<String> lines, Map<String, String> map) throws TimeCompileException {
	Objects.requireNonNull(lines);
	Objects.requireNonNull(map);

	IntervalsReader reader = new IntervalsReader();
	List<Interval> intervals = reader.convert(lines);

	Map<String, Category> categoriesMap = new HashMap<>();
	for (Interval interval : intervals) {
	    List<String> tags = interval.getTags();
	    if (tags.size() == 0) {
		// Nothing to record.
	    } else if (tags.size() == 1) {
		String tag = TagTransformer.transform(map, tags.get(0));
		if (tag.equals("")) {
		    // Skip.
		} else {
		    Category category = categoriesMap.computeIfAbsent(tag, (k) -> new Category(tag));
		    category.addInterval(interval);
		}
	    } else {
		System.err.println("Ignoring multitag interval " + interval);
	    }
	}

	List<Category> categories = categoriesMap.values().stream().filter(c -> !c.getTag().isEmpty())
		.collect(toList());

	Duration totalMinutes = Duration
		.ofMinutes(categories.stream().map(Category::getDuration).mapToInt(Duration::getMinutes).sum());

	Comparator<Category> compareByDecreasingDuration = (a, b) -> {
	    return b.getDuration().getMinutes() - a.getDuration().getMinutes();
	};
	categories.sort(compareByDecreasingDuration);

	return new Summary(categories, totalMinutes);
    }

}
