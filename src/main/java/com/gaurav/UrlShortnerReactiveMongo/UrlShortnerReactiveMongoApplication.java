package com.gaurav.UrlShortnerReactiveMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class UrlShortnerReactiveMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerReactiveMongoApplication.class, args);  
	}
 
}
 