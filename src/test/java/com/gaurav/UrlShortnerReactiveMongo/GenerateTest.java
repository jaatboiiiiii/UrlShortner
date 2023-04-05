 package com.gaurav.UrlShortnerReactiveMongo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GenerateTest extends BaseTest {

//	@Autowired
//	private WebClient webClient;
//	@Test
//	public void postTest() {
//	Mono<UrlDto> urlDto = this.webClient
//			.post()
//			.uri("/create")
//			.bodyValue(UrlDto("https://www.youtube.com/"))
//			.retrieve()
//			.bodyToMono(UrlDto.class)
//			.doOnNext(System.out::println);
//
//
//StepVerifier.create(urlDto)
//.expectNextCount(1)
//.verifyComplete();
//}
//	private UrlDto UrlDto(String string) {
//		UrlDto urlDto =new  UrlDto("",string,"",null,null,1,8);
//		// TODO Auto-generated method stub
//		return urlDto;
////		return null;
//	}
//	 @Autowired
//	    private WebClient webTestClient;
//	  
//	
//	  
//	  @Test
//	  public void postTest() {
//		  UrlDto urlDto = new UrlDto("","https://www.youtube.com/","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8);
//		  
//		  
//		  Mono<UrlDto> res=this.webTestClient
//		  .post()
//		  .uri("/create")
//		  .bodyValue(urlDto)
//		  .retrieve()
//		  .bodyToMono(UrlDto.class);
//		   
//		  StepVerifier.create(res)
//		  .expectNextCount(1)
//		  .expectComplete();
//		  
//	  } 
//	  @Test
//	  public void postTestWhenEmpty() {
//		  UrlDto urlDto = new UrlDto("2","","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8);
//		  Mono<UrlDto> res=this.webTestClient
//				  .post()
//				  .uri("/create")
//				  .bodyValue(urlDto)
//				  .retrieve()
//				  .bodyToMono(UrlDto.class);
//		  
//		  StepVerifier.create(res).verifyError(WebClientResponseException.BadRequest.class);
//		  
//			  
//	  }
//	  @Test
//	  public void postWhenAlreadyExists() {
//		  UrlDto urlDto = new UrlDto("2","http://www.google.com","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8);
//		  Mono<UrlDto> res=this.webTestClient
//				  .post()
//				  .uri("/create")
//				  .bodyValue(urlDto)
//				  .retrieve()
//				  .bodyToMono(UrlDto.class).then(this.webTestClient
//						  .post()
//						  .uri("/create")
//						  .bodyValue(urlDto)
//						  .retrieve()
//						  .bodyToMono(UrlDto.class));
//		  StepVerifier.create(res).verifyError(WebClientResponseException.BadGateway.class);
//	  }
//	  
//	  @Test
//	  public void postWhenPuttingDomainExists() {
//		  UrlDto urlDto = new UrlDto("2","http://localhost:8080/12345","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8);
//		  Mono<UrlDto> res=this.webTestClient
//				  .post()
//				  .uri("/create")
//				  .bodyValue(urlDto)
//				  .retrieve()
//				  .bodyToMono(UrlDto.class);
//		  
//		  StepVerifier.create(res).verifyError(WebClientResponseException.BadRequest.class);
//	  }
//	  
}
