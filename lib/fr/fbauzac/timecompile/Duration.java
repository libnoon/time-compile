package fr.fbauzac.timecompile;

public final class Duration {

    private final int minutes;

    Duration(int minutes) {
	this.minutes = minutes;
    }

    public static Duration ofMinutes(int minutes) {
	return new Duration(minutes);
    }

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

    public int getMinutes() {
	return minutes;
    }

}
