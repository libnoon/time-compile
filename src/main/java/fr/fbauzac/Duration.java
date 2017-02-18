package fr.fbauzac;

public final class Duration {

    private int minutes;

    public Duration(int minutes) {
	this.minutes = minutes;
    }

    @Override
    public String toString() {
	int accu = minutes;
	int min = accu % 60;
	accu /= 60;
	int hour = accu % 24;
	accu /= 24;
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
