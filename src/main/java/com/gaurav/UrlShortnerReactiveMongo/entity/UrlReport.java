package com.gaurav.UrlShortnerReactiveMongo.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class UrlReport {
	@Id
	private String shortUrl;
	private String dateCreated;
	private Map<String,Long> fetchOnDate;
	private String status;
	
	@Override
	public String toString() {
		return "UrlReport [shortUrl=" + shortUrl + ", dateCreated=" + dateCreated + ", fetchOnDate=" + fetchOnDate
				+ ", status=" + status + "]";
	}
	public String getShortUrl() { 
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Map<String, Long> getFetchOnDate() {
		return fetchOnDate;
	}
	public void setFetchOnDate(Map<String, Long> fetchOnDate) {
		this.fetchOnDate = fetchOnDate;
	}
	
	public UrlReport(String shortUrl, String dateCreated, Map<String, Long> fetchOnDate, String status) {
		super();
		this.shortUrl = shortUrl;
		this.dateCreated = dateCreated;
		this.fetchOnDate = fetchOnDate;
		this.status = status;
	}
	public UrlReport() {
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
