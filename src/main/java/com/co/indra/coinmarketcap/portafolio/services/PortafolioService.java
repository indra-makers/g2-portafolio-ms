package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortafolioService {

    @Autowired
    private PortafolioRepository portafolioRepository;

    public void registerPortafolio(Portafolio portafolio) {
        List<Portafolio> portafolioByNamePortafolioAndUsername = portafolioRepository.findByNamePortafolioAndUsername(portafolio.getNamePortafolio(), portafolio.getUsername());

        if(!portafolioByNamePortafolioAndUsername.isEmpty()) {
            throw new BusinessException(ErrorCodes.PORTAFOLIO_WITH_NAME_EXISTS);
        }else{
            portafolioRepository.create(portafolio);
        }
    }

    public List<Portafolio> getPortafolioByUsername(String username) {
        if(portafolioRepository.findByUsername(username).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_WITH_USERNAME_NOT_EXISTS.getMessage());
        }else{
            return portafolioRepository.findByUsername(username);
        }

    }
}
