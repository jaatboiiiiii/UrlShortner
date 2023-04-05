package com.gaurav.UrlShortnerReactiveMongo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.UrlShortnerReactiveMongo.entity.UrlReport;

import reactor.core.publisher.Flux;

@Repository
public interface UrlReportRepository extends ReactiveMongoRepository<UrlReport, String>{
	
	Flux<UrlReport> findByDateCreated(String dateCreated);
	Flux<UrlReport> findByFetchOnDateRegex(String regex);
	
}
