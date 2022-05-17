package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PortafolioRepository portafolioRepository;

    public void registerAsset(Integer idPortafolio, String idSymbolCoin, Integer quantity, Double balanceAsset, Double dollarBalance){
        if (portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        else if(!assetRepository.findAssetByPortafolioAndAsset(idSymbolCoin, idPortafolio).isEmpty()) {
            throw new BusinessException(ErrorCodes.ASSET_ALREADY_EXISTS_IN_A_PORTAFOLIO);
        }
        assetRepository.createAsset(new Asset(idPortafolio, idSymbolCoin, quantity, balanceAsset, dollarBalance));
    }
}

