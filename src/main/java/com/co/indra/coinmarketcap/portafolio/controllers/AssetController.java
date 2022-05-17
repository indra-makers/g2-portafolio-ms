package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * http://localhost:8080/api/assets/portafolios
     *   POST /api/assets/portafolios
     * @param
     * @return 200 OK
     */
    @PostMapping("/portafolios")
    public void createAsset(@RequestBody Asset asset) {
        assetService.registerAsset(asset.getIdPortafolio(), asset.getIdSymbolCoin(), asset.getQuantity(), asset.getBalanceAsset(), asset.getDollarBalance());
    }
}
