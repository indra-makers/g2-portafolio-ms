package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.responses.UserResponse;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRepository {
    private final RestTemplate restTemplate;

    public UserRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserResponse getUserFromUsersmsByUsername(String username) {
        String url = "https://g2-users-ms.herokuapp.com/api/users-ms/users/" + username;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ErrorCodes.USERNAME_NOT_FOUND.getMessage());
        }
        ResponseEntity<UserResponse> responseUser = restTemplate.getForEntity(url, UserResponse.class);
        UserResponse body = responseUser.getBody();
        return new UserResponse(body.getUsername(), body.getMail(), body.getDisplayName(), body.getIdCategoryUser());
    }
}
