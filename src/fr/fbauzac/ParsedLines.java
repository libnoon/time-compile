package fr.fbauzac;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ParsedLines {
    private static final Pattern TIME_PATTERN = Pattern.compile("\\A(\\d\\d)h(\\d\\d)\\Z");

    public static ParsedLine parse(String line) {
	Matcher matcher = TIME_PATTERN.matcher(line);
	if (matcher.matches()) {
	    return new TimeParsedLine(line, matcher.group(1), matcher.group(2));
	} else {
	    return new ContentParsedLine(line);
	}
    }
}
