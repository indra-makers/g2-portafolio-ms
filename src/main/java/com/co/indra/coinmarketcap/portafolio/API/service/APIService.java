package com.co.indra.coinmarketcap.portafolio.API.service;

import com.co.indra.coinmarketcap.portafolio.API.clients.UserClient;
import com.co.indra.coinmarketcap.portafolio.api.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class APIService {

    @Autowired
    private UserClient userClient;

    public UserResponse getPostsPlainJSON(String username) {
        return userClient.getUserFromUsersmsByUsername(username);
    }
}
