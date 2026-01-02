package com.wallacyrezende.latencydiagnostics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient imdbRestClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.imdbapi.dev").build();
    }
}
