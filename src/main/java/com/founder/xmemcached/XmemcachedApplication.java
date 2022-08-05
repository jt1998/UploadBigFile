package com.founder.xmemcached;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@SpringBootApplication
public class XmemcachedApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmemcachedApplication.class, args);
    }



   @Bean
   public RestTemplate restTemplate(){
        return new RestTemplate();
   }
}
