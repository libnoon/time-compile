package fr.fbauzac;

import java.io.IOException;

public class TimeCompileException extends Exception {

    public TimeCompileException(String message, IOException cause) {
	super(message, cause);
    }

    public TimeCompileException(String message) {
	super(message);
    }

    private static final long serialVersionUID = 1L;

}
