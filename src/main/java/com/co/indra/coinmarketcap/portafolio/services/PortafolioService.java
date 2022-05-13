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
        List<Portafolio> portafolioByNamePortafolioAndMailUser = portafolioRepository.findByNamePortafolioAndMailUser(portafolio.getNamePortafolio(), portafolio.getMailUser());

        if(!portafolioByNamePortafolioAndMailUser.isEmpty()) {
            throw new BusinessException(ErrorCodes.PORTAFOLIO_WITH_NAME_EXISTS);
        }else{
            portafolioRepository.create(portafolio);
        }
    }

    public List<Portafolio> getPortafolioByMailUser(String mailUser) {
        if(portafolioRepository.findByMailUser(mailUser).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_WITH_MAIL_USER_NOT_EXISTS.getMessage());
        }else{
            return portafolioRepository.findByMailUser(mailUser);
        }

    }
}
