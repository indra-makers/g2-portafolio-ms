package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class History implements Serializable {

    private Long id;

    private Integer idPortafolio;

    private String idSymbolCoin;

    private Integer quantity;

    private Double balanceAsset;

    private Double dollarBalance;

    private Date historyDate;

    private Long avgBuyPrice;

    public History() {
    }

    public History(Long id, Integer idPortafolio, String idSymbolCoin, Integer quantity, Double balanceAsset, Double dollarBalance) {
        this.id = id;
        this.idPortafolio = idPortafolio;
        this.idSymbolCoin = idSymbolCoin;
        this.quantity = quantity;
        this.balanceAsset = balanceAsset;
        this.dollarBalance = dollarBalance;
        this.historyDate = new Date();

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

    public String getIdSymbolCoin() {
        return idSymbolCoin;
    }

    public void setIdSymbolCoin(String idSymbolCoin) {
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

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public Long getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(Long avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }
}
