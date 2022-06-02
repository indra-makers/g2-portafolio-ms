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
        try {
            ResponseEntity<UserResponse> responseUser = restTemplate.getForEntity(url, UserResponse.class);
            System.out.println(responseUser);
            if (responseUser.getStatusCode() != HttpStatus.OK) {
                throw new NotFoundException(ErrorCodes.USERNAME_NOT_FOUND.getMessage());
            }
            UserResponse body = responseUser.getBody();
            return new UserResponse(body.getUsername(), body.getMail(), body.getDisplayName(), body.getIdCategoryUser());
        }
        catch (Exception e){
            throw new NotFoundException(ErrorCodes.USERNAME_NOT_FOUND.getMessage());
        }

    }
}
