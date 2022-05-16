package com.co.indra.coinmarketcap.portafolio.model.entities;

import java.io.Serializable;


public class Portafolio implements Serializable {

    private Long id;

    private String username;

    private String namePortafolio;

    private Double balancePortafolio;

    public Portafolio() {
    }

    public Portafolio(Long id, String username, String namePortafolio, Double balancePortafolio) {
        this.id = id;
        this.username = username;
        this.namePortafolio = namePortafolio;
        this.balancePortafolio = balancePortafolio;
    }

    public Portafolio(String username, String namePortafolio, Double balancePortafolio) {
        this.username = username;
        this.namePortafolio = namePortafolio;
        this.balancePortafolio = balancePortafolio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamePortafolio() {
        return namePortafolio;
    }

    public void setNamePortafolio(String namePortafolio) {
        this.namePortafolio = namePortafolio;
    }

    public Double getBalancePortafolio() {
        return balancePortafolio;
    }

    public void setBalancePortafolio(Double balancePortafolio) {
        this.balancePortafolio = balancePortafolio;
    }
}
