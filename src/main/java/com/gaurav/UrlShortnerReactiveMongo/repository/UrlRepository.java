package com.gaurav.UrlShortnerReactiveMongo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.entity.Url;

import reactor.core.publisher.Mono;
@Repository
public interface UrlRepository extends ReactiveMongoRepository<Url,String>{

	Mono<Url> findByShortUrl(String shortUrl);

	Mono<Url> findByOrignalUrl(String orignalUrl);
	Mono<Boolean> existsByUserId(String userId);
	Mono<Url> findByUserIdAndOrignalUrl(String userId,String orignalUrl);
	Mono<Url> findByUserId(String userId);
	Mono<Url> findTopByUserId(String userId);
}
