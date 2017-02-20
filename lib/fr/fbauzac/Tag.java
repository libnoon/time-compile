package fr.fbauzac;

import java.util.Objects;

public final class Tag implements Comparable<Tag> {
    private String name;

    public Tag(String name) {
	Objects.requireNonNull(name);
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

    @Override
    public int hashCode() {
	return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Tag other = (Tag) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

}
