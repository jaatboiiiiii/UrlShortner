package com.gaurav.UrlShortnerReactiveMongo.exception;

public class UrlExpiredException  extends RuntimeException{
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UrlExpiredException(String msg) {
		super(msg);
		this.msg = msg;
	} 
	
}
