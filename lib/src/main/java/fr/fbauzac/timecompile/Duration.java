package fr.fbauzac.timecompile;

/**
 * Time duration.
 *
 * <p>The duration granularity is the minute.
 */
public final class Duration {

    /**
     * The duration as a count of minutes.
     */
    private final int minutes;

    /**
     * Construct a Duration from the given number of minutes.
     *
     * @param minutes the number of minutes.
     */
    private Duration(int minutes) {
	this.minutes = minutes;
    }

    /**
     * Build a Duration object from the given number of minutes.
     *
     * @param minutes the count of minutes.
     * @return the Duration object.
     */
    public static Duration ofMinutes(int minutes) {
	return new Duration(minutes);
    }

    /**
     * Get the duration of a work day in hours.
     *
     * <p>The duration of a work day is normally 7 hours, but can be
     * overridden by setting the fr.fbauzac.time-compile.day-length
     * property.
     *
     * @return the number of hours in a working day.
     */
    private static int getDayLength() {
	String str = System.getProperty("fr.fbauzac.time-compile.day-length");
	if (str != null) {
	    return Integer.parseInt(str);
	} else {
	    return 7;
	}
    }

    @Override
    public String toString() {
	int accu = minutes;
	int min = accu % 60;
	accu /= 60;
	int hour = accu % getDayLength();
	accu /= getDayLength();
	int days = accu;

	StringBuilder sb = new StringBuilder();
	if (days > 0) {
	    sb.append(days);
	    sb.append("d");
	}
	if (hour > 0) {
	    sb.append(hour);
	    sb.append("h");
	}
	if (min > 0) {
	    sb.append(min);
	    sb.append("m");
	}

	return sb.toString();
    }

    /**
     * Get the number of minutes in this Duration.
     *
     * @return the count of minutes.
     */
    public int getMinutes() {
	return minutes;
    }

}
