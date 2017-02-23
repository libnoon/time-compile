package fr.fbauzac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MapParser {

    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("\\A *([^ ]*) *: *(.*?) *\\Z");

    /**
     * Parse the given lines as a mapping.
     * 
     * @param lines
     *            the list of lines.
     * @return the mapping.
     */
    public static Map<String, String> parse(List<String> lines) {
	Map<String, String> map = new HashMap<>();

	for (String line : lines) {
	    if (line.startsWith("#")) {
		// Ignore comments
		continue;
	    }
	    Matcher matcher = KEY_VALUE_PATTERN.matcher(line);
	    if (matcher.matches()) {
		String target = matcher.group(1);

		for (String source : matcher.group(2).split(" +")) {
		    if (source.isEmpty()) {
			// Ignore.
		    } else {
			map.put(source, target);
		    }
		}
	    }
	}
	return map;

    }

}
