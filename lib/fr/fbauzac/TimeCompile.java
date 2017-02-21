package fr.fbauzac;

import static java.util.stream.Collectors.toList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class TimeCompile {

    public static Summary summarize(List<String> mapFileNames, List<String> lines) throws TimeCompileException {
	Objects.requireNonNull(mapFileNames);
	Objects.requireNonNull(lines);

	TagTransformer tagTransformer = new IdentityTagTransformer();
	for (String fileName : mapFileNames) {
	    Path path = Paths.get(fileName);
	    tagTransformer = new FileTagTransformer(path, tagTransformer);
	}

	return TimeCompile.processLines(lines, tagTransformer);
    }

    public static Summary processLines(List<String> lines, TagTransformer tagTransformer) throws TimeCompileException {
	Objects.requireNonNull(lines);
	Objects.requireNonNull(tagTransformer);

	IntervalsReader reader = new IntervalsReader();
	List<Interval> intervals = reader.convert(lines);

	Map<Tag, Category> categoriesMap = new HashMap<>();
	for (Interval interval : intervals) {
	    List<Tag> tags = interval.getTags();
	    if (tags.size() == 0) {
		// Nothing to record.
	    } else if (tags.size() == 1) {
		Tag tag = tagTransformer.transform(tags.get(0));
		if (tag.toString().equals("")) {
		    // Skip.
		} else {
		    Category tagInfo = ensureCategory(categoriesMap, tag);
		    tagInfo.addInterval(interval);
		}
	    } else {
		System.err.println("Ignoring multitag interval " + interval);
	    }
	}

	List<Category> categories = categoriesMap.values().stream().filter(c -> !c.getTag().getName().isEmpty())
		.collect(toList());

	Duration totalMinutes = Duration
		.ofMinutes(categories.stream().map(Category::getDuration).mapToInt(Duration::getMinutes).sum());

	Comparator<Category> compareByDecreasingDuration = (a, b) -> {
	    return b.getDuration().getMinutes() - a.getDuration().getMinutes();
	};
	categories.sort(compareByDecreasingDuration);

	return new Summary(categories, totalMinutes);
    }

    private static Category ensureCategory(Map<Tag, Category> tagInfos, Tag tag) {
	if (tagInfos.containsKey(tag)) {
	    // Nothing to do.
	} else {
	    tagInfos.put(tag, new Category(tag));
	}
	return tagInfos.get(tag);
    }

}
