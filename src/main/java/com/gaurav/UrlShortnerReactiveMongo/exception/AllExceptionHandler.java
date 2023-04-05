package com.gaurav.UrlShortnerReactiveMongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gaurav.UrlShortnerReactiveMongo.dto.ErrorDto;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class AllExceptionHandler {
	@ExceptionHandler(EmptyUrlException.class)
	Mono<ResponseEntity<?>> urlNotFound(EmptyUrlException ex){
		
		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage())); 
	}
	@ExceptionHandler(UrlExistsException.class)
	Mono<ResponseEntity<?>> urlExists(UrlExistsException ex){
		
		return Mono.just(new ResponseEntity<ErrorDto>(new ErrorDto(ex.getMessage(),ex.getShortUrl()),HttpStatus.BAD_GATEWAY));
//		return Mono.just(ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage(),ex.getShortUrl())));
	}
	@ExceptionHandler(NotCorrectUrlException.class)
	Mono<ResponseEntity<?>> notCorrectUrl(NotCorrectUrlException ex){
		
		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
	}
	@ExceptionHandler(UrlExpiredException.class)
	Mono<ResponseEntity<?>> urlExpired(UrlExpiredException ex){
		
		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
	}
	@ExceptionHandler(UrlIsNotFound.class)
	Mono<ResponseEntity<Object>> urlNotPresent(UrlIsNotFound ex){
		
//		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
		return Mono.just(new ResponseEntity<Object>(ex.getMessage(),HttpStatus.NOT_FOUND));
	}
	@ExceptionHandler(UserIdNotValidException.class)
	Mono<ResponseEntity<?>> urlExpired(UserIdNotValidException ex){
		
		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
	}
	@ExceptionHandler(NoDataOnThisDateException.class)
	Mono<ResponseEntity<?>> noDataOnDate(NoDataOnThisDateException ex){
		
		return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
	}
}
