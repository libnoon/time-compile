package fr.fbauzac;

public final class Tag implements Comparable<Tag> {
    private String name;

    public Tag(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public int compareTo(Tag other) {
	return name.compareTo(other.name);
    }

}
