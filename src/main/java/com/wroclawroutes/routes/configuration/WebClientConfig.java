package com.wroclawroutes.routes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${app.graphhopperApi.baseUrl}")
    private String addressBaseUrl;
    @Value("${app.graphhopperApi.key}")
    private String apiKey;
    //private String addressBaseUrl = "https://graphhopper.com/api/1/route?point=51.09168897683583, 17.0323881125119&point=51.082669590243256, 17.04849029529289&profile=foot&locale=en&calc_points=false&key=ace1a284-def5-4ddc-a897-d639a484ff60";
    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(addressBaseUrl+"?key="+apiKey).build();
    }
}
