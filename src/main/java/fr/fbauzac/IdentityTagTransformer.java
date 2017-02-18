package fr.fbauzac;

public final class IdentityTagTransformer implements TagTransformer {

    @Override
    public Tag transform(Tag tag) {
	return tag;
    }

    @Override
    public String toString() {
	return "IdentityTagTransformer";
    }

}
