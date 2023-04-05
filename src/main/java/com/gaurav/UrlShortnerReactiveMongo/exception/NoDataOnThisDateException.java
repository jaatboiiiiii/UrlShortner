package com.gaurav.UrlShortnerReactiveMongo.exception;

public class NoDataOnThisDateException extends RuntimeException{
String msg;

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public NoDataOnThisDateException(String msg) {
	super(msg);
	this.msg = msg;
}

}
