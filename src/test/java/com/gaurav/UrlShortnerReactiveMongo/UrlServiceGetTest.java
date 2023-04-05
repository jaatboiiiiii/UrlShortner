package com.gaurav.UrlShortnerReactiveMongo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.dto.UrlReportDto;
import com.gaurav.UrlShortnerReactiveMongo.entity.Url;
import com.gaurav.UrlShortnerReactiveMongo.entity.UrlReport;
import com.gaurav.UrlShortnerReactiveMongo.exception.NoDataOnThisDateException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlExpiredException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlIsNotFound;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlReportRepository;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlRepository;
import com.gaurav.UrlShortnerReactiveMongo.service.UrlService;
import com.gaurav.UrlShortnerReactiveMongo.utility.Utils;

import de.flapdoodle.embed.process.collections.Collections;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UrlServiceGetTest extends BaseTest{

	@InjectMocks
	private UrlService service;
	 
	@Mock
	private UrlRepository repo;
	@Mock
	private UrlReportRepository reportRepo;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Test
	public void successGet() {
//		UrlDto urlDto = new UrlDto("","",Instant.now(),Instant.now(),1,8);
		UrlDto urlDto =new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,new HashMap<>());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		UrlReport mockReport= new UrlReport("abc12345",Instant.now().atZone(ZoneId.systemDefault()).toString(),new HashMap<>(),"up");
//		System.out.println(urlDto);
		Mockito.when(repo.findByShortUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(reportRepo.findById("abc12345")).thenReturn(Mono.just(mockReport));
		Mockito.when(reportRepo.save(Mockito.any(UrlReport.class))).thenReturn(Mono.just(mockReport));
//		System.out.println(mockurl); 
		Mono<UrlDto> res = service.findOrignal("abc12345");
		res.doOnNext(System.out::println);
		StepVerifier.create(res).expectNextMatches(u -> u.getOrignalUrl().equals(mockurl.getOrignalUrl())).verifyComplete();
	}
	
	
	@Test
	public void notAvailable() {
//		UrlDto urlDto = new UrlDto("","",Instant.now(),Instant.now(),1,8);
		UrlDto urlDto =new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,new HashMap<>());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(urlDto);
		Mockito.when(repo.findByShortUrl(Mockito.any(String.class))).thenReturn(Mono.empty());
//		Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		
		System.out.println(mockurl);
		Mono<UrlDto> res = service.findOrignal("abc12345");
		
		StepVerifier.create(res).expectError(UrlIsNotFound.class).verify();
	}
	
	@Test
	public void urlExpired() {
//		UrlDto urlDto = new UrlDto("","",Instant.now(),Instant.now(),1,8);
		UrlDto urlDto =new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now(),1,8,new HashMap<>());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		UrlReport mockReport= new UrlReport("abc12345",Instant.now().atZone(ZoneId.systemDefault()).toString(),new HashMap<>(),"up");

		System.out.println(urlDto);
		Mockito.when(repo.findByShortUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.delete(Mockito.any(Url.class))).thenReturn(Mono.empty());
		Mockito.when(reportRepo.findById("abc12345")).thenReturn(Mono.just(mockReport));
		Mockito.when(reportRepo.save(Mockito.any(UrlReport.class))).thenReturn(Mono.just(mockReport));
		System.out.println(mockurl);
		Mono<UrlDto> res = service.findOrignal("abc12345");
		
		StepVerifier.create(res).expectError(UrlExpiredException.class).verify();
	}
	
	@Test
	public void trackTest() {
		
		UrlDto urlDto =new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,new HashMap<>());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(urlDto);
		Mockito.when(repo.findByShortUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
//		Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
//		Mockito.when(repo.delete(Mockito.any(Url.class))).thenReturn(Mono.empty());
		System.out.println(mockurl);
		Mono<UrlDto> res = service.track("abc12345");
		
		StepVerifier.create(res).expectNextMatches(u -> u.getOrignalUrl().equals(mockurl.getOrignalUrl())).verifyComplete();
		
	}
	@Test 
	public void getDataForDateTest() {
		
		UrlReport mockreport = new UrlReport("abc12345",Instant.now().atZone(ZoneId.systemDefault()).toString(),new HashMap<>() ,"up");
		when(reportRepo.findByDateCreated(any(String.class))).thenReturn(Flux.just(mockreport));
		Flux<UrlReportDto> res =service.getAllByDate(Instant.now().atZone(ZoneId.systemDefault()).toString());
	
	StepVerifier.create(res).expectNextMatches(u -> u.getDateCreated() == mockreport.getDateCreated()).verifyComplete();
	}
	
	@Test
	public void getDataForDateTestForException() {
	
		Mockito.when(reportRepo.findByDateCreated(Mockito.any(String.class))).thenReturn(Flux.empty());
		Flux<UrlReportDto> res = service.getAllByDate("2022-07-09");
		StepVerifier.create(res).expectError(NoDataOnThisDateException.class).verify();
	}
	@Test
	public void getDataForFetchTest() {
		UrlReport mockreport = new UrlReport("abc12345",Instant.now().atZone(ZoneId.systemDefault()).toString(),new HashMap<>() ,"up");
		Map<String, Long> tk = new HashMap<>();
		tk.put("2023-03-31", 3L);
		mockreport.setFetchOnDate(tk);
		when(reportRepo.findAll()).thenReturn(Flux.just(mockreport));
	Flux<UrlReportDto> res =service.getfetchByDate("2023-03-31");
	
	StepVerifier.create(res).expectNextMatches(u -> u.getFetchOnDate().equals( mockreport.getFetchOnDate())).verifyComplete();
	
	}

	@Test
	public void getDataForFetchForException() {
		when(reportRepo.findAll()).thenReturn(Flux.empty());
		Flux<UrlReportDto> res =service.getfetchByDate("2023-03-31");
		
		StepVerifier.create(res).expectError(NoDataOnThisDateException.class).verify();
	}
}
