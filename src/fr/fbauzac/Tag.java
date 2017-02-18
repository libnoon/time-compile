package fr.fbauzac;

public final class Tag {
    private String name;

    public Tag(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return name;
    }

}
