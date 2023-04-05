package com.gaurav.UrlShortnerReactiveMongo.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.dto.UrlReportDto;
import com.gaurav.UrlShortnerReactiveMongo.service.UrlService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

@RestController
public class UrlController {
	
	@Autowired
	UrlService service;
	@PostMapping("/create")
	public Mono<UrlDto> createShortLink(@RequestBody Mono<UrlDto> urlDto){
		
		return service.generateShortUrl(urlDto);
		
}	
	 
	@GetMapping("/{shortUrl}")
	public Mono<ResponseEntity<UrlDto>> followLink(@PathVariable String shortUrl,ServerHttpResponse response){
		return service.findOrignal(shortUrl).map(u -> u.getOrignalUrl()).doOnNext(System.out::println)
				.map(
						url -> {
							return ResponseEntity
								      .status(HttpStatus.TEMPORARY_REDIRECT)
								      .location(URI.create(url))
								      .build();
		 				}
						);		  
	}
	@GetMapping("{shortUrl}/track")
	public Mono<UrlDto> trackUrl(@PathVariable String shortUrl){
			return service.track(shortUrl);
		
	}
 	
	@GetMapping("/createDate")
	public Flux<UrlReportDto> getUrlByDate(@RequestParam String date){
		return service.getAllByDate(date);
	}
	
	@GetMapping("/fetchByDate")
	public Flux<UrlReportDto> getFetchDetailsByDate(@RequestParam String date){
			return service.getfetchByDate(date);
	}
}

