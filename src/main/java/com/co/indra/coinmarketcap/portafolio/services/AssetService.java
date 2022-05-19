package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public void deleteAsset(String symboliCoin, int idPortafolio) {

        if(assetRepository.assetFindByIdPortafolio(idPortafolio).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        else if(assetRepository.assetFindByPortafolioAndSimbolicoin(idPortafolio,symboliCoin).isEmpty()) {
            throw new NotFoundException(ErrorCodes.SYMBOL_COIN_NOT_FOUND.getMessage());
        }else{
            assetRepository.deleteAsset(symboliCoin, idPortafolio);
        }
    }
}
