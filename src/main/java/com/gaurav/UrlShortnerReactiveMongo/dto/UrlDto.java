package com.gaurav.UrlShortnerReactiveMongo.dto;
//package com.gaurav.UrlShortnerReactive.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class UrlDto {

	private String id; 
	private String orignalUrl;
	private String shortUrl;
	private Instant creationTime;
	private Instant expirationTime;
	private int uptimeInMin;
	private int noOfCharInHash;
	private String userId;

	private Map<String, Long> fetchCounts;
	public String getId() {
		return id; 
	} 
	public void setId(String id) {
		this.id = id;
	}
	public String getOrignalUrl() {
		return orignalUrl;
	}
	public void setOrignalUrl(String orignalUrl) {
		this.orignalUrl = orignalUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Instant getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Instant creationTime) {
		this.creationTime = creationTime;
	}
	public Instant getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Instant expirationTime) {
		this.expirationTime = expirationTime;
	}

	public UrlDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "UrlDto [id=" + id + ", orignalUrl=" + orignalUrl + ", shortUrl=" + shortUrl + ", creationTime="
				+ creationTime + ", expirationTime=" + expirationTime + ", uptimeInMin=" + uptimeInMin
				+ ", noOfCharInHash=" + noOfCharInHash + ", userId=" + userId + ", fetchCounts=" + fetchCounts + "]";
	}
	
	public UrlDto(String id, String orignalUrl, String shortUrl, Instant creationTime, Instant expirationTime,
			int uptimeInMin, int noOfCharInHash, Map<String, Long> fetchCounts) {
		super();
		this.id = id;
		this.orignalUrl = orignalUrl;
		this.shortUrl = shortUrl;
		this.creationTime = creationTime;
		this.expirationTime = expirationTime;
		this.uptimeInMin = uptimeInMin;
		this.noOfCharInHash = noOfCharInHash;
		this.fetchCounts = fetchCounts;
	}
	public UrlDto(String id, String orignalUrl, String shortUrl, Instant creationTime,
			Instant expirationTime, int uptimeInMin, int noOfCharInHash) {
		super();
		this.id = id;
		this.orignalUrl = orignalUrl;
		this.shortUrl = shortUrl;
		this.creationTime = creationTime;
		this.expirationTime = expirationTime;
		this.uptimeInMin = uptimeInMin;
		this.noOfCharInHash = noOfCharInHash;
	}
	
	public UrlDto(String id, String orignalUrl, String shortUrl, Instant creationTime, Instant expirationTime,
			int uptimeInMin, int noOfCharInHash, String userId, Map<String, Long> fetchCounts) {
		super();
		this.id = id;
		this.orignalUrl = orignalUrl;
		this.shortUrl = shortUrl;
		this.creationTime = creationTime;
		this.expirationTime = expirationTime;
		this.uptimeInMin = uptimeInMin;
		this.noOfCharInHash = noOfCharInHash;
		this.userId = userId;
		this.fetchCounts = fetchCounts;
	}
	public int getUptimeInMin() {
		return uptimeInMin;
	}
	public void setUptimeInMin(int uptimeInMin) {
		this.uptimeInMin = uptimeInMin;
	}
	public int getNoOfCharInHash() {
		return noOfCharInHash;
	}
	public void setNoOfCharInHash(int noOfCharInHash) {
		this.noOfCharInHash = noOfCharInHash;
	}
	public Map<String, Long> getFetchCounts() {
		return fetchCounts;
	}
	public void setFetchCounts(Map<String, Long> fetchCounts) {
		this.fetchCounts = fetchCounts;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
