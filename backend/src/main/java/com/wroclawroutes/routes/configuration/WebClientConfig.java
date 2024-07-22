package com.wroclawroutes.routes.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${app.graphhopperApi.baseUrl}")
    private String addressBaseUrl;
    @Value("${app.graphhopperApi.reverseGeoCoding}")
    private String addressBaseUrlReverseGeocoding;
    @Value("${app.graphhopperApi.key}")
    private String apiKey;
    @Bean(name = "routeClient")
    public WebClient webClient(){
        return WebClient.builder().baseUrl(addressBaseUrl+"?key="+apiKey).build();
    }
    @Bean(name = "reverseGeocodingClient")
    public WebClient webClientReverseGeoCoding(){
        return WebClient.builder().baseUrl(addressBaseUrlReverseGeocoding+"?key="+apiKey).build();
    }
}
