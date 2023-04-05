package com.gaurav.UrlShortnerReactiveMongo.exception;

public class NotCorrectUrlException extends RuntimeException {
	
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public NotCorrectUrlException(String msg) {
		super(msg);
		this.msg = msg;
	} 
	
	
	
}
