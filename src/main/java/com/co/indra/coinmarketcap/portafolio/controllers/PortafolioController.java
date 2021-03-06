package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.requests.FirstTransaction;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import com.co.indra.coinmarketcap.portafolio.model.responses.ListPortfolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.PortafoliosDistributionResponse;
import com.co.indra.coinmarketcap.portafolio.services.AssetService;
import com.co.indra.coinmarketcap.portafolio.services.PortafolioService;
import com.co.indra.coinmarketcap.portafolio.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    public void addPortafolioToUser(@Valid @RequestBody Portafolio portafolio) throws IOException {
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

    /**
     * http://localhost:8081/api/portafolio/portafolios/{id_portafolio}/assets/{id_symbolCoin}
     * GET portafolios/users/{username}/portafolios
     */
    @DeleteMapping("/{id_portafolio}/assets/{id_symbolcoin}")
    public void deleteAsset( @PathVariable("id_symbolcoin") String idSymbolCoin, @PathVariable("id_portafolio") int idPortafolio) {
        assetService.deleteAsset(idSymbolCoin,idPortafolio);
    }

    /**
     * http://localhost:8081/api/portafolio/portafolios/{id_portafolio}/assets/{id_symbolCoin}
     * GET portafolios/users/{username}/portafolios
     */
    @PutMapping(Routes.ID_PORTAFOLIO_PATH+Routes.PORTAFOLIO_BY_NAME_PATH)
    public void editarPortafolio(@PathVariable("id_portafolio") int idPortafolio, @PathVariable("name_portafolio") String newName) {
        portafolioService.editarNamePortafolio(newName, idPortafolio);

    }
    /** http://localhost:8080/api/portafolio-ms/portafolios/{username}
     * GET /api/portafolios
     * @return 200 OK
     */
    @GetMapping(Routes.ID_USER_PATH)
    public ListPortfolio getPorfoliosByUsername(@PathVariable("username") String username) {
        return portafolioService.getPortfoliosByUser(username);
    }

     /** http://localhost:8081/api/portafolio-ms/portafolios/summary/1
     * GET distribution/1
     * @param idPortafolio
     * @return
     */
    @GetMapping(Routes.DISTRIBUTION_BY_IDPORTAFOLIO_PATH)
    public PortafoliosDistributionResponse getAllSummary(@PathVariable("idPortafolio") Integer idPortafolio) {
        return assetService.getAllSummary(idPortafolio);
    }
     /**
     * http://localhost:8080/api/portafolio-ms/portafolios/{{idPortafolio}}
     *   DELETE /api/portafolio-ms/portafolios/{{idPortafolio}}
     * @param
     * @return 200 OK
     */
    @DeleteMapping(Routes.ID_PORTAFOLIO_PATH)
    public void deletePortafolio(@PathVariable("id_portafolio") Integer idPortafolio) {
        portafolioService.deletePortafolio(idPortafolio);
    }
	
	@GetMapping(Routes.CREATE_ASSET_IN_PORTAFOLIO_BY_IDPORTAFOLIO_PATH)
    public List<Asset> getAssetsByPortafolio(@PathVariable("idPortafolio") int idPortafolio){
        return assetService.listAssetByPortafolio(idPortafolio);
    }

    /**
     * http://localhost:8081/api/portafolio/portafolios/assets/transactions/{id_transaction}
     * PUT portafolios/assets/transactions/{id_transaction}
     */
    @PutMapping("assets/transactions/{id_transaction}")
    public void editarTransaction(@PathVariable("id_transaction") Long idTransaction, @RequestBody Transaction transaction) {
        transactionService.editarTransaccionById(transaction, idTransaction);
    }


    /**
     * http://localhost:8080/api/portafolios/{idPortafolio}/assets/{idAssets}/transaction
     * GET portafolios/{idPortafolio}/assets/{idAssets}/transaction
     */
    @GetMapping(Routes.ADD_TRANSACTION_TO_ASSET)
    public List<Transaction> getTransactionsOfAAsset(
            @PathVariable("idPortafolio") int idPortafolio, @PathVariable("idAssets") Long idAsset) {

        return portafolioService.getTransactionsOfAAsset(idPortafolio, idAsset);
    }
}
