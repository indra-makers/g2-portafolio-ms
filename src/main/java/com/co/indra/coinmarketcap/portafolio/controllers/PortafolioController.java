package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.services.AssetService;
import com.co.indra.coinmarketcap.portafolio.services.PortafolioService;
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

    /**
     * http://localhost:8081/api/portafolio/portafolios
     * PATH /portafolios
     * POST
     */
    @PostMapping
    public void addPortafolioToUser(@Valid @RequestBody Portafolio portafolio) {
        portafolioService.registerPortafolio(portafolio);
    }

    /**
     * http://localhost:8081/api/portafolio/portafolios/users/{{username}}/portafolios
     * GET portafolios/users/{username}/portafolios
     */
    @GetMapping(Routes.PORTAFOLIO_BY_USER_PATH)
    public List<Portafolio> getPortafoliosUserName(
            @PathVariable("username") String username) {
        return portafolioService.getPortafolioByUsername(username);
    }

    /**
     * http://localhost:8081/api/portafolio/portafolios/{id_portafolio}/assets/{id_symbolCoin}
     * Delete portafolios/{id_portafolio}/assets/{id_symbolcoin}
     */
    @DeleteMapping(Routes.PORTAFOLIO_PATH +Routes.ID_PORTAFOLIO_PATH + Routes.PORTAFOLIO_BY_SYMBOLCOIN_PATH)
    public void delete( @PathVariable("id_symbolcoin") String idSymbolCoin, @PathVariable("id_portafolio") int idPortafolio) {
        assetService.deleteAsset(idSymbolCoin,idPortafolio);
    }

}
