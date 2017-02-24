package fr.fbauzac.timecompile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TagTransformer {

    /**
     * Apply the given mapping on the given string recursively.
     * 
     * This function can detect loops.
     * 
     * @param map
     *            the mapping.
     * @param in
     *            the string to map recursively.
     * @return the mapped string.
     * @throws TagTransformLoop
     *             when an infinite loop is detected.
     */
    static String transform(Map<String, String> map, String in) throws TagTransformLoop {
	String current = in;
	List<String> loopRecord = new ArrayList<>();
	loopRecord.add(current);
	while (true) {
	    if (map.containsKey(current)) {
		current = map.get(current);
		loopRecord.add(current);
	    } else {
		return current;
	    }
	    if (current.equals(in)) {
		throw new TagTransformLoop(
			String.format("Tag transformation: loop detected: %s", String.join(" ", loopRecord)));
	    }
	}
    }
}
