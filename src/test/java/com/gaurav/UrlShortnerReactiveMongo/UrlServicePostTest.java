package com.gaurav.UrlShortnerReactiveMongo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.entity.Url;
import com.gaurav.UrlShortnerReactiveMongo.entity.UrlReport;
import com.gaurav.UrlShortnerReactiveMongo.exception.EmptyUrlException;
import com.gaurav.UrlShortnerReactiveMongo.exception.NotCorrectUrlException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UrlExistsException;
import com.gaurav.UrlShortnerReactiveMongo.exception.UserIdNotValidException;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlReportRepository;
import com.gaurav.UrlShortnerReactiveMongo.repository.UrlRepository;
import com.gaurav.UrlShortnerReactiveMongo.service.UrlService;
import com.gaurav.UrlShortnerReactiveMongo.utility.Utils;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UrlServicePostTest extends BaseTest {
	
	@InjectMocks
	private UrlService service;
	@Mock
	private UrlRepository repo;
	@Mock
	private UrlReportRepository reportRepo;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Test 
	public void postsucessTest() {
	UrlDto urlDto =(new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,"90182u98",new HashMap<>()));
	UrlReport mockReport= new UrlReport("abc12345",Instant.now().atZone(ZoneId.systemDefault()).toString(),new HashMap<>(),"up");

	Url mockurl = Utils.dtoToEntity(urlDto);	
	Mockito.when(repo.findTopByUserId(Mockito.any(String.class))).thenReturn(Mono.just (new Url("","https://www.google.com","reqwt123",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,new HashMap<>(),"90182u98")));

//	Mockito.when(repo.findByOrignalUrl(Mockito.any(String.class))).thenReturn(Mono.empty());
	Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
	Mockito.when(repo.findByUserIdAndOrignalUrl(Mockito.any(String.class),Mockito.any(String.class))).thenReturn(Mono.empty());
	when(reportRepo.save(any(UrlReport.class))).thenReturn(Mono.just(mockReport));
		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto));
//		System.out.println(res.map(re -> re.toString()));
		StepVerifier.create(res).expectNextMatches(r -> r.getShortUrl().equals(mockurl.getShortUrl())
				).verifyComplete();
		} 
	@Test
	public void alreadyExists() {
		
		UrlDto urlDto1 =(new UrlDto("","https://www.youtube.com/","",null,null,1,8,"jjadksjbk2",new HashMap<>()));
		
		
		UrlDto urlDto =(new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,"jjadksjbk2",new HashMap<>()));
		
		System.out.println(urlDto.hashCode());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(mockurl);
		System.out.println(Utils.entityToDto(mockurl));
		Mockito.when(repo.findByUserIdAndOrignalUrl(Mockito.any(String.class),Mockito.any(String.class))).thenReturn(Mono.just(mockurl));

//		Mockito.when(repo.findByOrignalUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
		//Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.findTopByUserId(Mockito.any(String.class))).thenReturn(Mono.just (mockurl));

		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto1));
			
		StepVerifier.create(res).expectError(UrlExistsException.class).verify();
			
	}
	@Test
	public void shorLinkHasDomainName() {
		UrlDto urlDto =(new UrlDto("","http://localhost:8080/vamos","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,"jjadksjbk2",new HashMap<>()));
		
		System.out.println(urlDto.hashCode());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(mockurl);
		System.out.println(Utils.entityToDto(mockurl));
		
//		Mockito.when(repo.findByOrignalUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
		//Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.findTopByUserId(Mockito.any(String.class))).thenReturn(Mono.just (new Url("","https://www.google.com","reqwt123",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,new HashMap<>(),"jjadksjbk2")));
		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto));
		StepVerifier.create(res).expectError(NotCorrectUrlException.class).verify();
	}
	@Test
	public void emptyLink() {
		UrlDto urlDto =(new UrlDto("","","",null,null,1,8,"",new HashMap<>()));
		System.out.println(urlDto.hashCode());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(mockurl);
		System.out.println(Utils.entityToDto(mockurl));
		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto));
		StepVerifier.create(res).expectError(EmptyUrlException.class).verify();
}
	@Test
	public void urlWithSameUserId() {
	UrlDto urlDto =(new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,"76776576fu",new HashMap<>()));
		
		System.out.println(urlDto.hashCode());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(mockurl);
		System.out.println(Utils.entityToDto(mockurl));
		
//		Mockito.when(repo.findByOrignalUrl(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
//		Mockito.when(repo.save(Mockito.any(Url.class))).thenReturn(Mono.just(mockurl));
		Mockito.when(repo.findByUserIdAndOrignalUrl(Mockito.any(String.class),Mockito.any(String.class)))
		.thenReturn(Mono.just(mockurl));
		Mockito.when(repo.findTopByUserId(Mockito.any(String.class))).thenReturn(Mono.just(mockurl));
		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto));
			
		StepVerifier.create(res).expectError(UrlExistsException.class).verify();
	}
	@Test
	public void userIdNotPresent() {
	UrlDto urlDto =(new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8,"76776576fu",new HashMap<>()));
		
		System.out.println(urlDto.hashCode());
		Url mockurl = Utils.dtoToEntity(urlDto);	
		System.out.println(mockurl);
		System.out.println(Utils.entityToDto(mockurl));
		Mockito.when(repo.findTopByUserId(Mockito.any(String.class))).thenReturn(Mono.empty());
		Mono<UrlDto> res = service.generateShortUrl(Mono.just(urlDto));
			
		StepVerifier.create(res).expectError(UserIdNotValidException.class).verify();
	}
	}	
	
	

