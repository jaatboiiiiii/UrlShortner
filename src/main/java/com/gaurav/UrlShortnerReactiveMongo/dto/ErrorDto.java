package com.gaurav.UrlShortnerReactiveMongo.dto;

public class ErrorDto {
	private String msg;
	private String shortUrl;
	
	 

	public ErrorDto(String msg, String shortUrl) {
		this.msg = msg;
		this.shortUrl = shortUrl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ErrorDto(String msg) {
		super();
		this.msg = msg;
	}

	public ErrorDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	
}
