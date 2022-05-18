package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.FirstTrasaction;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PortafolioRepository portafolioRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public void registerAsset(Integer idPortafolio, String idSymbolCoin, String typeTransaction, Double actualPrice, Double fee, String note, int amount){
        if (portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        if(!assetRepository.findAssetByPortafolioAndAsset(idSymbolCoin, idPortafolio).isEmpty()) {
            throw new BusinessException(ErrorCodes.ASSET_ALREADY_EXISTS_IN_A_PORTAFOLIO);
        }
        assetRepository.createAsset(new Asset(idPortafolio, idSymbolCoin, amount, (double) amount, (amount*actualPrice)));
        List<Asset> lst = assetRepository.getIdAssetByPortafolioAndIdSymbolCoin(idSymbolCoin, idPortafolio);
        Long idAsset;
        idAsset = lst.get(0).getId();
        transactionRepository.createFirstTransaction(idAsset, new FirstTrasaction(typeTransaction, actualPrice, fee, note, (amount+actualPrice+fee), amount));
    }


}

