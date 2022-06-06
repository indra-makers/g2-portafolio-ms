package com.co.indra.coinmarketcap.portafolio.API.service;

import com.co.indra.coinmarketcap.portafolio.API.client.UserClient;
import com.co.indra.coinmarketcap.portafolio.API.model.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class APIService {

    @Autowired
    private UserClient userClient;

    public UserApi getPostsPlainJSON(String username) {
        return userClient.getPostsPlainJSON(username);
    }
}
