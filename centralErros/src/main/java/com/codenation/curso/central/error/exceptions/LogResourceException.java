package com.codenation.curso.central.error.exceptions;

public class LogResourceException extends Exception {

	private static final long serialVersionUID = 1L;

	public LogResourceException() {
	        super();
	    }

	    public LogResourceException(String message) {
	        super(message);
	    }

	    public LogResourceException(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public LogResourceException(Throwable cause) {
	        super(cause);
	    }

	    public LogResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	        super(message, cause, enableSuppression, writableStackTrace);
	    }
}
