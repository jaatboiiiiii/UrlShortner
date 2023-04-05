package com.gaurav.UrlShortnerReactiveMongo.dto;

import java.util.Map;

public class UrlReportDto {

	private String shortUrl;
	private String dateCreated;
	private Map<String,Long> fetchOnDate;
	private String status;
	
	@Override
	public String toString() {
		return "UrlReportDto [shortUrl=" + shortUrl + ", dateCreated=" + dateCreated + ", fetchOnDate=" + fetchOnDate
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
	
	public UrlReportDto(String shortUrl, String dateCreated, Map<String, Long> fetchOnDate, String status) {
		super();
		this.shortUrl = shortUrl;
		this.dateCreated = dateCreated;
		this.fetchOnDate = fetchOnDate;
		this.status = status;
	}
	public UrlReportDto() {
	
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
