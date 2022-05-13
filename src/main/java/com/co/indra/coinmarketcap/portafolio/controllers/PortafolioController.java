package com.co.indra.coinmarketcap.portafolio.controllers;

import com.co.indra.coinmarketcap.portafolio.config.Routes;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
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

    /**
     * PATH /portafolios
     * POST
     */
    @PostMapping
    public void addPortafolioToUser(@Valid @RequestBody Portafolio portafolio) {
        portafolioService.registerPortafolio(portafolio);
    }

    /**
     * GET portafolios/users/{mailUser}/portafolios
     */
    @GetMapping(Routes.PORTAFOLIO_BY_USER_PATH)
    public List<Portafolio> getPortafoliosUser(
            @PathVariable("mailUser") String mailUser) {

        return portafolioService.getPortafolioByMailUser(mailUser);
    }

}
