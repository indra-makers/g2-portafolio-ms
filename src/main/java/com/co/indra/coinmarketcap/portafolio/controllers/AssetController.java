package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.services.AssetService;
import com.co.indra.coinmarketcap.portafolio.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Routes.ASSETS_RESOURCE)
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private TransactionService transactionService;

    /**
     * http://localhost:8080/api/assets/portafolios
     *   POST /api/assets/portafolios
     * @param
     * @return 200 OK
     */
    @PostMapping(Routes.PORTAFOLIO_PATH)
    public void createAsset(@RequestBody Asset asset) {
        assetService.registerAsset(asset.getIdPortafolio(), asset.getIdSymbolCoin(), asset.getQuantity(), asset.getBalanceAsset(), asset.getDollarBalance());
    }

    @PostMapping(Routes.ADD_TRANSACTION_TO_ASSET)
    public void createTransactionToAsset (@PathVariable("id_assets") Long id,@Valid @RequestBody Transaction transaction){
        transactionService.createTransaction(transaction, id);
    }

}
