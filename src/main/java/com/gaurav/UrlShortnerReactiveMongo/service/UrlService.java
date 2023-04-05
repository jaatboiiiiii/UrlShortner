package com.gaurav.UrlShortnerReactiveMongo.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.dto.UrlReportDto;
import com.gaurav.UrlShortnerReactiveMongo.entity.UrlReport;
import com.gaurav.UrlShortnerReactiveMongo.exception.EmptyUrlException;
import com.gaurav.UrlShortnerReactiveMongo.exception.NoDataOnThisDateException;
import com.gaurav.UrlShortnerReactiveMongo.exception.NotCorrectUrlException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlExistsException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlExpiredException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlIsNotFound;
import com.gaurav.UrlShortnerReactiveMongo.exception.UserIdNotValidException;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlReportRepository;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlRepository;
import com.gaurav.UrlShortnerReactiveMongo.utility.Utils;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service 
public class UrlService {
	@Autowired
	UrlRepository repo;
	@Autowired
	UrlReportRepository reportRepo;
	
		Logger logger = LoggerFactory.getLogger(UrlService.class);
	public Mono<UrlDto> findByShortUrl(String shortUrl){
		return repo.findByShortUrl(shortUrl).map(Utils:: entityToDto); 
	}
	//the function to add object
	public Mono<UrlDto> generateShortUrl(Mono<UrlDto> urlDto){
				
		return urlDto.flatMap(url -> {
			if(url.getUserId()=="") {
				return getUserWhenIdNotGiven(url);
			}
			else {
				return getUserWhenIdGiven(url);
			}
		});
	}
	
	
	//getting the orignalUrl with shortUrl
	public Mono<UrlDto> findOrignal(String shortUrl) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return Mono.just(shortUrl).flatMap(s -> repo.findByShortUrl(s))
				.switchIfEmpty(Mono.error(new UrlIsNotFound("This Url is not a shortUrl From the Database")))
				.map(url ->{
					String fetchDate = formatter.format(Instant.now().atZone(ZoneId.systemDefault()));
					url.getFetchCounts().compute(fetchDate, (key, value) -> value == null ? 1 : value + 1);
					return url;
				}
				).flatMap(url -> repo.save(url))
				.map(Utils::entityToDto)
				.flatMap(u -> checkExpiration(u)).flatMap(u -> updateReport(u));
	}
	//Inserting and checking when the user has given id
	private Mono<UrlDto> getUserWhenIdGiven(UrlDto url) {
	return Mono.just(url).flatMap(u -> checkUserId(u))
			 .filter(u -> u.getOrignalUrl()!="")
			 .switchIfEmpty(Mono.error(new EmptyUrlException("the Url field is empty")))
			 .filter(u -> !u.getOrignalUrl().contains("localhost"))
			 .switchIfEmpty(Mono.error(new NotCorrectUrlException("the link is already a short link")))
			 .flatMap(u -> repo.findByUserIdAndOrignalUrl(u.getUserId(), u.getOrignalUrl())).map(Utils::entityToDto)
			 .flatMap(generatedUrl -> 
			 Mono.error(new UrlExistsException("the Url already exists with ShortLink and same userId"
			 		,generatedUrl.getShortUrl()))).then(Mono.just(url))
	.doOnNext(  
			 u -> {
				 u.setNoOfCharInHash(charInHash(u.getNoOfCharInHash()));
				 u.setShortUrl(encodedUrl(u.getOrignalUrl(),u.getNoOfCharInHash())); 
				 u.setCreationTime(Instant.now());
				 
				 u.setUptimeInMin(fillpTime(u.getUptimeInMin()));
				 u.setExpirationTime(getExpirationTime(u.getExpirationTime(), u.getCreationTime(),u.getUptimeInMin()));
				 u.setFetchCounts(new HashMap<>());
				 
			 } 
			 ) .flatMap(u -> repo.save(Utils.dtoToEntity(u)))
	 .map(Utils::entityToDto)
	 .flatMap(u -> createReport(u))
	 .doOnNext(u -> u.setShortUrl("http://localhost:8080/".concat(u.getShortUrl()))); 
	

}

	//checking if the id is present in the db or not
	private Mono<UrlDto> checkUserId(UrlDto ue) {

return	Mono.just(ue)
		.flatMap(u -> repo.findTopByUserId(u.getUserId()))
		.switchIfEmpty(Mono.error(new UserIdNotValidException("this userId is not present ")))
		.map(u -> ue);
		
}

	//inserting and checking when the user has not given id.
	private Mono<UrlDto> getUserWhenIdNotGiven(UrlDto url) {
	return Mono.just(url)
	.filter(u -> u.getOrignalUrl()!="") 
	 .switchIfEmpty(Mono.error(new EmptyUrlException("the Url field is empty")))
	 
	 .filter(u -> !u.getOrignalUrl().contains("localhost"))
	 .switchIfEmpty(Mono.error(new NotCorrectUrlException("the link is already a short link")))
	 .doOnNext( 
			 u -> { 
				 u.setNoOfCharInHash(charInHash(u.getNoOfCharInHash()));
				 u.setShortUrl(encodedUrl(u.getOrignalUrl(),u.getNoOfCharInHash()));
				 u.setCreationTime(Instant.now());
				 
				 u.setUptimeInMin(fillpTime(u.getUptimeInMin()));
				 u.setExpirationTime(getExpirationTime(u.getExpirationTime(), u.getCreationTime(),u.getUptimeInMin()));
				 u.setFetchCounts(new HashMap<>());
				 u.setUserId(getuserId(u));
			 } 
			 ) .flatMap(u -> repo.save(Utils.dtoToEntity(u)))
	 .map(Utils::entityToDto)
	 .flatMap(u -> createReport(u)).doOnNext(u -> u.setShortUrl("http://localhost:8080/".concat(u.getShortUrl()))); 
		 
} 
	
	//creating a unique id for the user when thhe user has not given id
	private String getuserId(UrlDto u) {
			return encodedUrl(u.getOrignalUrl().concat("userId"),10);
		}

	//creating report for the shortLink
	private Mono<UrlDto> createReport(UrlDto u) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		UrlReportDto urlReportDto = new UrlReportDto();
		return Mono.just(urlReportDto).doOnNext( urlrep ->{
		urlrep.setDateCreated(formatter.format(Instant.now().atZone(ZoneId.systemDefault())));
		urlrep.setShortUrl(u.getShortUrl());
		urlrep.setStatus("Up and Running");
		urlrep.setFetchOnDate(new HashMap<>());
		}).map(urlrep -> Utils.reportDtoToreportEntity(urlReportDto)).flatMap(urlrep -> reportRepo.save(urlrep)).then(Mono.just(u));
		
	}
	
	//to get the default NoOfChar in hash
	private int charInHash(int noOfCharInHash) {
	
		if(noOfCharInHash == 0) return 7;
		else {
			return noOfCharInHash;
		}
	}
	//to get default UpTime for the shortLink
	private int fillpTime( int uptime) {
		if(uptime==0)
			return 2;
		else {return uptime;}
				
	}
	//to get the default expiration date based on the Uptime
	private Instant getExpirationTime(Instant expirationTime, Instant creationTime,int uptimeInMin) {
		if(expirationTime == null) return creationTime.plus(uptimeInMin,ChronoUnit.MINUTES);
		else return expirationTime;
	}
	
	//to get the unique shortUrl for the given orignalLink
	public String encodedUrl(String orignal,int hashNo) {
		
		return Hashing.sha256()
        .hashString(orignal.concat(LocalDateTime.now().toString()), Charsets.UTF_8)
        .toString()
        .substring(0, hashNo);
    }

	//to update the report for the fetches 
	private Mono<UrlDto> updateReport(UrlDto u) {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	Mono<UrlReport> urlReport = reportRepo.findById(u.getShortUrl());
	return	urlReport.doOnNext(r -> {
			String fetchDate = formatter.format(Instant.now().atZone(ZoneId.systemDefault()));
			r.getFetchOnDate().compute(fetchDate, (key, value) -> value == null ? 1 : value + 1);
		}).flatMap(r -> reportRepo.save(r)).then(Mono.just(u));
	
}

	
	//to check if the link has expired or not 
	private Mono<UrlDto> checkExpiration(UrlDto u) {
	return Mono.just(u).flatMap(
				result -> {
					if(result.getExpirationTime().compareTo(Instant.now()) < 0) {
						System.out.println(u.toString());
						Mono<UrlReport> urlReport = reportRepo.findById(result.getShortUrl());
						return repo.delete(Utils.dtoToEntity(u))
								
								.then(urlReport.doOnNext(report -> report.setStatus("Down")).flatMap(report -> reportRepo.save(report)))
			
							.then(Mono.error(new UrlExpiredException("This Url is expired and will be deleted from dataBase!!!")));
						}
					else {
					return Mono.just(result);
					}
				}
			);
			
}

	// pt return the entity matching the shorurl
	public Mono<UrlDto> track(String shortUrl) {
	return Mono.just(shortUrl).flatMap(s -> repo.findByShortUrl(s)).map(Utils::entityToDto);
}

	// to get report by created Date that all the link will come out here that were created on this date
	public Flux<UrlReportDto> getAllByDate(String date) {
		return reportRepo.findByDateCreated(date)  
				.switchIfEmpty(Mono.error(new NoDataOnThisDateException("no data for this date")))
				.map(r -> Utils.reportEntityToreportDto(r));
}
	
	//to get all the links that were fetched on this date
	public Flux<UrlReportDto> getfetchByDate(String date) {
			return reportRepo.findAll().filter(it -> it.getFetchOnDate().containsKey(date)).map(Utils::reportEntityToreportDto).doOnNext(
						u -> {
						String tokeep = date;
						Long fetch = u.getFetchOnDate().get(date);
						Map<String, Long> tk = new HashMap<>();
						tk.put(tokeep, fetch);
						u.setFetchOnDate(tk);
						}
					).switchIfEmpty(Mono.error(new NoDataOnThisDateException("not data found on this date!!!")));
}
}
