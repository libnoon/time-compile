package fr.fbauzac.timecompile;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public final class TagLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    TagLabel(String tag) {
	super(tag);
	final int BORDER = 10;
	setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
    }
}
