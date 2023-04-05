package com.gaurav.UrlShortnerReactiveMongo.exception;

public class UserIdNotValidException  extends RuntimeException{
	private String msg;
	
	
	public UserIdNotValidException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
