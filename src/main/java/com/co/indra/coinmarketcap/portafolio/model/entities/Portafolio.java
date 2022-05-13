package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


public class Portafolio implements Serializable {

    private Long id;

    private String mailUser;

    private String name;

    private Double balancePortafolio;

    public Portafolio() {
    }

    public Portafolio(Long id, String mailUser, String name, Double balancePortafolio) {
        this.id = id;
        this.mailUser = mailUser;
        this.name = name;
        this.balancePortafolio = balancePortafolio;
    }

    public Portafolio(String mailUser, String name, Double balancePortafolio) {
        this.mailUser = mailUser;
        this.name = name;
        this.balancePortafolio = balancePortafolio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalancePortafolio() {
        return balancePortafolio;
    }

    public void setBalancePortafolio(Double balancePortafolio) {
        this.balancePortafolio = balancePortafolio;
    }
}
