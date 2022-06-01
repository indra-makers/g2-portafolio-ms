package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.model.requests.FirstTransaction;
import com.co.indra.coinmarketcap.portafolio.model.responses.PortafoliosDistribution;
import com.co.indra.coinmarketcap.portafolio.model.responses.PortafoliosDistributionResponse;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.HistoryAssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private HistoryAssetRepository historyAssetRepository;

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
        historyAssetRepository.addHistory(new Asset(lst.get(0).getId(), lst.get(0).getIdPortafolio(), lst.get(0).getIdSymbolCoin(), lst.get(0).getQuantity(), lst.get(0).getBalanceAsset(), lst.get(0).getDollarBalance()));
        Transaction transaction = new Transaction(Math.toIntExact(idAsset), firstTrasaction.getTypeTransaction(), firstTrasaction.getDate(), firstTrasaction.getActualPrice(), firstTrasaction.getFee(), firstTrasaction.getNotes(), firstTrasaction.getQuantity(), (firstTrasaction.getQuantity()* firstTrasaction.getActualPrice()+ firstTrasaction.getFee()), (int) (firstTrasaction.getQuantity()* firstTrasaction.getActualPrice()));
        portafolioService.createTransaction(transaction, idPortafolio, idAsset);
    }


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
	
	public List<Asset> listAssetByPortafolio(int idPortafolio){
        if(portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        return assetRepository.assetFindByIdPortafolio(idPortafolio);
    }

    public PortafoliosDistributionResponse getAllSummary(Integer idPortafolio) {
        if (portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }

        List<PortafoliosDistribution> list = assetRepository.getSummary(idPortafolio);
        return new PortafoliosDistributionResponse(list);
    }


}

