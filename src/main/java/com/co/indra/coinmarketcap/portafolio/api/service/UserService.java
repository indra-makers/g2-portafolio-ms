package com.co.indra.coinmarketcap.portafolio.api.service;

import com.co.indra.coinmarketcap.portafolio.api.clients.UserClient;
import com.co.indra.coinmarketcap.portafolio.api.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserService {

    @Autowired
    UserClient userClient;

    public UserResponse getUserFromUsersmsByUsername(String username) throws IOException {
        return userClient.getUserFromUsersmsByUsername(username);
    }
}
