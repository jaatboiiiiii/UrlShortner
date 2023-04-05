package com.gaurav.UrlShortnerReactiveMongo.exception;

public class UrlExistsException  extends RuntimeException{
	private String msg;
	private String shortUrl;
	
	public UrlExistsException(String msg, String shortUrl) {
		super(msg);
		this.msg = msg;
		this.shortUrl = shortUrl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	} 

	public UrlExistsException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
		
	

}
