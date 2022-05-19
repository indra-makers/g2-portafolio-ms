package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.model.requests.FirstTransaction;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PortafolioRepository portafolioRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PortafolioService portafolioService;

    public void registerAsset(Integer idPortafolio, FirstTransaction firstTrasaction){
        if (portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        if(!assetRepository.findAssetByPortafolioAndAsset(firstTrasaction.getIdSymbolCoin(), idPortafolio).isEmpty()) {
            throw new BusinessException(ErrorCodes.ASSET_ALREADY_EXISTS_IN_A_PORTAFOLIO);
        }
        assetRepository.createAsset(new Asset(idPortafolio, firstTrasaction.getIdSymbolCoin(), firstTrasaction.getQuantity(), (firstTrasaction.getActualPrice()* firstTrasaction.getQuantity()), (firstTrasaction.getActualPrice()* firstTrasaction.getQuantity())));
        List<Asset> lst = assetRepository.getIdAssetByPortafolioAndIdSymbolCoin(firstTrasaction.getIdSymbolCoin(), idPortafolio);
        Long idAsset =  lst.get(0).getId();
        Transaction transaction = new Transaction(Math.toIntExact(idAsset), firstTrasaction.getTypeTransaction(), firstTrasaction.getDate(), firstTrasaction.getActualPrice(), firstTrasaction.getFee(), firstTrasaction.getNotes(), firstTrasaction.getQuantity(), (firstTrasaction.getQuantity()* firstTrasaction.getActualPrice()* firstTrasaction.getFee()), (int) (firstTrasaction.getQuantity()* firstTrasaction.getActualPrice()));
        portafolioService.createTransaction(transaction, idPortafolio, idAsset);

        //assetRepository.createAsset(new Asset(idPortafolio, idSymbolCoin, quantity, balanceAsset, dollarBalance));
        //portafolioRepository.recalculateBalanceToPortfolio(idPortafolio);

        /*assetRepository.createAsset(new Asset(idPortafolio, idSymbolCoin, amount, (double) amount, (amount*actualPrice)));
        List<Asset> lst = assetRepository.getIdAssetByPortafolioAndIdSymbolCoin(idSymbolCoin, idPortafolio);
        Long idAsset;
        idAsset = lst.get(0).getId();
        transactionRepository.createFirstTransaction(idAsset, new FirstTrasaction(typeTransaction, actualPrice, fee, note, (amount+actualPrice+fee), amount));
        */
    }


}

