package com.gaurav.UrlShortnerReactiveMongo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class UrlShortnerReactiveMongoApplicationTests {
	 @Test
	 void contextLoads() {
		 
	 }
}
//@WebMvcTest(EmptyUrlException.class)
//public class UrlShortnerReactiveMongoApplicationTests {
//
//    @Autowired
//    private WebClient webClient;
// 
//    @MockBean
//    private UrlService service;
//
//    @Test
//    public void testDivideByZero() throws Exception {
//    	UrlDto urlDto = new UrlDto("","","abc12345",Instant.now(),Instant.now().plus(1,ChronoUnit.MINUTES),1,8);
//        String expectedMessage = "the Url field is empty";
//
//        when(service.generateShortUrl(Mono.just(urlDto))).thenThrow(new EmptyUrlException(expectedMessage));
//        Mono<UrlDto> res=this.webClient
//				  .post()
//				  .uri("/create")
//				  .bodyValue(urlDto)
//				  
//				  .retrieve()
//				  .bodyToMono(UrlDto.class);
//        this.webClient.get().uri()
        
//        mockMvc.perform(post("/create",urlDto))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value(expectedMessage));
//    }
//}