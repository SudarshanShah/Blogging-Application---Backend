package com.blog.apis.exceptions;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -6612066364063294767L;

	public ApiException(String message) {
		super(message);
	}

	public ApiException() {
		super();
	}

	
}
