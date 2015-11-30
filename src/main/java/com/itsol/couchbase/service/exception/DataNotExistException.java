package com.itsol.couchbase.service.exception;

@SuppressWarnings("serial")
public class DataNotExistException extends RuntimeException{
	/**
	 * Constructor for DataExistedException.
	 * @param msg the detail message
	 */
	public DataNotExistException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for DataExistedException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using a underlying
	 * data access API such as JDBC)
	 */
	public DataNotExistException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
