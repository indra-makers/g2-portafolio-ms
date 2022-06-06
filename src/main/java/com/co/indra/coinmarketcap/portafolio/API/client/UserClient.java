package com.co.indra.coinmarketcap.portafolio.API.client;

import com.co.indra.coinmarketcap.portafolio.API.model.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    public UserClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${api.userClient.url}")
    private String apiUrl;

    public UserApi getPostsPlainJSON(String username) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(apiUrl).path(username);
        return this.restTemplate.getForObject(uri.toUriString(), UserApi.class);

    }
}
