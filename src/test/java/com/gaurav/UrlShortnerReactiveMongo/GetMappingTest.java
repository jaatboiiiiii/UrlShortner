package com.gaurav.UrlShortnerReactiveMongo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.gaurav.UrlShortnerReactiveMongo.dto.ErrorDto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetMappingTest extends BaseTest{
	@Autowired
	private WebClient webClient;
		
	 
//	  @Test
//		public void passingshortUrlPresentInDb()
//		{
//			Mono<ResponseEntity<Void>> res=this.webClient
//					.get() 
//					.uri("5e929028")
//					.retrieve() 
//					.toBodilessEntity()
//					.doOnNext(System.out::println);
//			
//			StepVerifier
////			.create(res).expectNextMatches(url -> url.getHeaders().getLocation().equals("https://github.com/Leoat12/url-shortening-api/tree/master/src/main/java/com/leoat/urlshorteningapp/service"))
//			.create(res).expectNextMatches(url->url.getStatusCode().equals(HttpStatus.TEMPORARY_REDIRECT))
//			.verifyComplete();
//			
//		}
//	  @Test
//	  public void passingshortUrlNotPresentInDb() {
//		  Mono<ErrorDto> res =this.webClient
//				  	.get()
//				  	.uri("/{shorturl}","1234")
//				  	.retrieve()
//				  	.bodyToMono(ErrorDto.class);
//		  StepVerifier
//		  .create(res).verifyError(WebClientResponseException.NotFound.class);
//	  }
//	  @Test
//	  public void passingShortUrlExpired() {
//		  // demo data to inserted
////		  UrlDto urlDto = new UrlDto("4","http://www.youtube.com","abc12345",Instant.now(),Instant.now(),0,8);
////		  Mono<UrlDto> res=this.webClient
////				  .post()
////				  .uri("/create")
////				  .bodyValue(urlDto)
////				  .retrieve()
////				  .bodyToMono(UrlDto.class);
//		  
//		  Mono<ErrorDto> res1 =this.webClient
//						  .get()
//						  .uri("/{shorturl}","a204630b")
//						  .retrieve() 
//						  .bodyToMono(ErrorDto.class);
//
//		  StepVerifier.create(res1)
//		  .verifyError(WebClientResponseException.BadRequest.class);
//		  
//	  }
}
