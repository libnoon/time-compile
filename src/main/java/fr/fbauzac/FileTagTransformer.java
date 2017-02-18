package fr.fbauzac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fbauzac.Tag;
import fr.fbauzac.TagTransformer;
import fr.fbauzac.TimeCompileException;

public final class FileTagTransformer implements TagTransformer {

    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("\\A *([^ ]*) *: *([^ ]+)\\Z");

    private Path path;
    private TagTransformer nextTagTransformer;

    /**
     * Target of the transformation (can be null).
     */
    private String target;

    @Override
    public String toString() {
	return String.format("FileTagTransformer(\"%s\", %s)", path, nextTagTransformer);
    }

    private Set<String> sources = new HashSet<>();

    public FileTagTransformer(Path path, TagTransformer tagTransformer) throws TimeCompileException {
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
	    Matcher matcher = KEY_VALUE_PATTERN.matcher(line);
	    if (matcher.matches()) {
		this.target = matcher.group(1);

		for (String source : matcher.group(2).split(" +")) {
		    if (source.isEmpty()) {
			// Ignore.
		    } else {
			this.sources.add(source);
		    }
		}
	    }
	}

    }

    @Override
    public Tag transform(Tag tag) {
	Tag newTag = nextTagTransformer.transform(tag);
	if (sources.contains(newTag.toString())) {
	    System.err.format("Transforming tag {%s} to {%s}%n", newTag.toString(), target);
	    return new Tag(target);
	} else {
	    return newTag;
	}
    }

}
