package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


public class Portafolio implements Serializable {

    private Long id;

    private String mailUser;

    private String namePortafolio;

    private Double balancePortafolio;

    public Portafolio() {
    }

    public Portafolio(Long id, String mailUser, String namePortafolio, Double balancePortafolio) {
        this.id = id;
        this.mailUser = mailUser;
        this.namePortafolio = namePortafolio;
        this.balancePortafolio = balancePortafolio;
    }

    public Portafolio(String mailUser, String namePortafolio, Double balancePortafolio) {
        this.mailUser = mailUser;
        this.namePortafolio = namePortafolio;
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
