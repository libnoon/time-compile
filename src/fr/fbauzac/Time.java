package fr.fbauzac;

public final class Time {

    private int hour;
    private int minute;

    public Time(int hour, int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    public String toString() {
	return String.format("%02dh%02d", hour, minute);
    }

    public int durationTo(Time other) {
	return other.inMinutes() - this.inMinutes();
    }

    private int inMinutes() {
	return hour * 60 + minute;
    }
}
