package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.requests.FirstTransaction;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.services.AssetService;
import com.co.indra.coinmarketcap.portafolio.services.PortafolioService;
import com.co.indra.coinmarketcap.portafolio.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Routes.PORTAFOLIO_PATH)
public class PortafolioController {

    @Autowired
    private PortafolioService portafolioService;
    @Autowired
    private AssetService assetService;

    @Autowired
    private TransactionService transactionService;

    /**
     * http://localhost:8081/api/portafolios
     * PATH /portafolios
     * POST
     */
    @PostMapping
    public void addPortafolioToUser(@Valid @RequestBody Portafolio portafolio) {
        portafolioService.registerPortafolio(portafolio);
    }

    /**
     * http://localhost:8081/api/portafolios/users/{{username}}/portafolios
     * GET portafolios/users/{username}/portafolios
     */
    @GetMapping(Routes.PORTAFOLIO_BY_USER_PATH)
    public List<Portafolio> getPortafoliosUserName(
            @PathVariable("username") String username) {

        return portafolioService.getPortafolioByUsername(username);
    }
    
    /**
     * http://localhost:8080/api/portafolios/{idPortafolio}/assets/{idAssets}/transaction
     * POST /api/portafolios
     * @param idPortafolio, idAssets
     * @return 200 OK
     */
    @PostMapping(Routes.ADD_TRANSACTION_TO_ASSET)
    public void createTransactionToAsset (@PathVariable ("idPortafolio") Integer idPortfolio, @PathVariable("idAssets") Long idAsset,@Valid @RequestBody Transaction transaction){
        portafolioService.createTransaction(transaction, idPortfolio, idAsset);
    }

    /**
     * http://localhost:8080/api/portafolios/{{idPortafolio}}/assets
     *   POST /api/portafolios/{{idPortafolio}}/assets
     * @param
     * @return 200 OK
     */
    @PostMapping(Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH)
    public void createAsset(@PathVariable("idPortafolio") Integer idPortafolio, @RequestBody FirstTransaction firstTrasaction) {
        assetService.registerAsset(idPortafolio, firstTrasaction);
    }


}
