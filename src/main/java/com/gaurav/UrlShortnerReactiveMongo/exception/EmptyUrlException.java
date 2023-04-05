package com.gaurav.UrlShortnerReactiveMongo.exception;

public class EmptyUrlException extends RuntimeException {

	private String msg;

	public EmptyUrlException(String msg) {
		super(msg);
		this.msg= msg;
		// TODO Auto-generated constructor stub
	}

	
}
 