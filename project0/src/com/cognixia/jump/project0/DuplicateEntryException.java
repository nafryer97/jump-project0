/**
 * 
 */
package com.cognixia.jump.project0;

/**
 * @author Noah Fryer
 * 
 * Exception thrown when duplicate IDs are inserted into Employee list.
 *
 */
public class DuplicateEntryException extends Exception {

	private static final long serialVersionUID = 5362824476082358544L;

	/**
	 * @param message
	 */
	public DuplicateEntryException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicateEntryException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicateEntryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DuplicateEntryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
