package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileTagTransformer implements TagTransformer {

    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("\\A *([^ ]*) *: *(.*?) *\\Z");

    private Path path;
    private TagTransformer nextTagTransformer;

    private Map<String, String> map = new HashMap<>();

    @Override
    public String toString() {
	return String.format("FileTagTransformer(\"%s\", %s)", path, nextTagTransformer);
    }

    FileTagTransformer(Path path, TagTransformer tagTransformer) throws TimeCompileException {
	this.nextTagTransformer = tagTransformer;
	this.path = path;

	List<String> lines;
	try {
	    lines = Files.readAllLines(path);
	} catch (IOException e) {
	    String message = String.format("cannot read %s%n", path);
	    throw new TimeCompileException(message, e);
	}
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

    }

    @Override
    public Tag transform(Tag tag) {
	Tag newTag = nextTagTransformer.transform(tag);
	if (map.containsKey(newTag.getName())) {
	    return new Tag(map.get(newTag.getName()));
	} else {
	    return newTag;
	}
    }

}
