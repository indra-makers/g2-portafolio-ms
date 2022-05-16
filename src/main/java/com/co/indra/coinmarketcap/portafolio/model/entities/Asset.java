package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import java.io.Serializable;


public class Asset implements Serializable {

    private Long id;

    private Integer idPortafolio;

    private Integer idSymbolCoin;

    private Integer quantity;

    private Double balanceAsset;

    private Double dollarBalance;

    public Asset() {
    }

    public Asset(Long id, Integer idPortafolio, Integer idSymbolCoin, Double balanceAsset, Double dollarBalance) {
        this.id = id;
        this.idPortafolio = idPortafolio;
        this.idSymbolCoin = idSymbolCoin;
        this.balanceAsset = balanceAsset;
        this.dollarBalance = dollarBalance;
    }

    public Asset(Integer idPortafolio, Integer idSymbolCoin, Double balanceAsset, Double dollarBalance) {
        this.idPortafolio = idPortafolio;
        this.idSymbolCoin = idSymbolCoin;
        this.balanceAsset = balanceAsset;
        this.dollarBalance = dollarBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPortafolio() {
        return idPortafolio;
    }

    public void setIdPortafolio(Integer idPortafolio) {
        this.idPortafolio = idPortafolio;
    }

    public Integer getIdSymbolCoin() {
        return idSymbolCoin;
    }

    public void setIdSymbolCoin(Integer idSymbolCoin) {
        this.idSymbolCoin = idSymbolCoin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getBalanceAsset() {
        return balanceAsset;
    }

    public void setBalanceAsset(Double balanceAsset) {
        this.balanceAsset = balanceAsset;
    }

    public Double getDollarBalance() {
        return dollarBalance;
    }

    public void setDollarBalance(Double dollarBalance) {
        this.dollarBalance = dollarBalance;
    }
}
