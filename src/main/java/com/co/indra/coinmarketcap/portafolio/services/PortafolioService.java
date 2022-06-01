package com.co.indra.coinmarketcap.portafolio.services;

import com.co.indra.coinmarketcap.portafolio.config.ErrorCodes;
import com.co.indra.coinmarketcap.portafolio.exceptions.BusinessException;
import com.co.indra.coinmarketcap.portafolio.exceptions.NotFoundException;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.model.responses.ListPortfolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.UsersPortfolios;
import com.co.indra.coinmarketcap.portafolio.repositories.AssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.HistoryAssetRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.PortafolioRepository;
import com.co.indra.coinmarketcap.portafolio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortafolioService {

    @Autowired
    private PortafolioRepository portafolioRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private HistoryAssetRepository historyAssetRepository;


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

    public void createTransaction(Transaction transaction, Integer idPortfolio ,Long idAsset){
        if(portafolioRepository.findPortafolioByIdPortafolio(idPortfolio).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        if(assetRepository.findAssetById(idAsset).isEmpty()){
            throw new NotFoundException(ErrorCodes.ASSET_DOES_NOT_EXISTS.getMessage());
        }
        if(transaction.getQuantity() <= 0 ){
            throw new BusinessException(ErrorCodes.TRANSACTION_INVALID_QUANTITY);
        }

        List <Asset> asset=assetRepository.findAssetById(idAsset);
        asset.get(0).setId(idAsset);
        if(transaction.getTypeTransaction().equals("buy")){
            historyAssetRepository.addHistory(asset.get(0));
            assetRepository.updateQuantityAsset(idAsset,  asset.get(0).getQuantity()+ transaction.getQuantity());
            transactionRepository.createTransaction(transaction, idAsset);
            assetRepository.recalculateAvgBuyPriceToAsset(idAsset);
        }
        else if(transaction.getTypeTransaction().equals("sell")){
            if(asset.get(0).getQuantity() < transaction.getQuantity()){
                throw new BusinessException(ErrorCodes.ASSET_QUANTITY_DONT_SUPPORT_SELL);
            }
            historyAssetRepository.addHistory(asset.get(0));
            assetRepository.updateQuantityAsset(idAsset,asset.get(0).getQuantity() - transaction.getQuantity() );
            transactionRepository.createTransaction(transaction, idAsset);
        }


    }

    public ListPortfolio getPortfoliosByUser(String username){
        if(portafolioRepository.findByUsername(username).isEmpty()){
            throw new BusinessException(ErrorCodes.PORTAFOLIO_WITH_USERNAME_NOT_EXISTS);
        }
        return new ListPortfolio(portafolioRepository.getPortfoliosByUser(username), portafolioRepository.getSumOfBalancePortfolios(username));

    }


    public void editarNamePortafolio(String newName, Integer id){
        if(portafolioRepository.findPortafolioByIdPortafolio(id).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }else{
            portafolioRepository.editarPortafolio(newName, id);
        }
    }

    public void deletePortafolio(int idPortafolio){
        if (portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()) {
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        if(!assetRepository.findAssetInPortafolioByIdPortafolio((long) idPortafolio).isEmpty()){
            throw new BusinessException(ErrorCodes.PORTFOLIO_CANNOT_BE_DELETED);
        }
        portafolioRepository.deletePortafolio((long) idPortafolio);
    }


    public List<Transaction> getTransactionsOfAAsset(int idPortafolio, Long idAsset) {
        if(portafolioRepository.findPortafolioByIdPortafolio(idPortafolio).isEmpty()){
            throw new NotFoundException(ErrorCodes.PORTAFOLIO_NOT_FOUND.getMessage());
        }
        if(assetRepository.findAssetById(idAsset).isEmpty()){
            throw  new NotFoundException(ErrorCodes.ASSET_DOES_NOT_EXISTS.getMessage());
        }

        return transactionRepository.findTransactionByIdAsset(idAsset.intValue());

    }
}
