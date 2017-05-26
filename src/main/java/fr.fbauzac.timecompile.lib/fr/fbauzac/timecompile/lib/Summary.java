package fr.fbauzac.timecompile.lib;

import java.util.Collections;
import java.util.List;

public final class Summary {
    private final List<Category> categories;
    private final Duration totalDuration;

    public Summary(List<Category> categories, Duration totalDuration) {
	this.categories = categories;
	this.totalDuration = totalDuration;
    }

    public List<Category> getCategories() {
	return Collections.unmodifiableList(categories);
    }

    public Duration getTotalDuration() {
	return totalDuration;
    }
}
