package com.co.indra.coinmarketcap.portafolio.model.entities;

import java.io.Serializable;
import java.util.Date;

public class FirstTrasaction implements Serializable {
    private Long idTransaction;
    private String idSymbolCoin;
    private String typeTransaction;
    private Date date;
    private Double actualPrice;
    private Double fee;
    private String notes;
    private Double totalRecived;
    private int amount;

    public FirstTrasaction() {
    }

    public FirstTrasaction(Long idTransaction, String idSymbolCoin, String typeTransaction, Date date, Double actualPrice, Double fee, String notes, Double totalRecived, int amount) {
        this.idTransaction = idTransaction;
        this.idSymbolCoin = idSymbolCoin;
        this.typeTransaction = typeTransaction;
        this.date = date;
        this.actualPrice = actualPrice;
        this.fee = fee;
        this.notes = notes;
        this.totalRecived = totalRecived;
        this.amount = amount;
    }

    public FirstTrasaction(String idSymbolCoin, String typeTransaction, Date date, Double actualPrice, Double fee, String notes, Double totalRecived, int amount) {
        this.idSymbolCoin = idSymbolCoin;
        this.typeTransaction = typeTransaction;
        this.date = date;
        this.actualPrice = actualPrice;
        this.fee = fee;
        this.notes = notes;
        this.totalRecived = totalRecived;
        this.amount = amount;
    }

    public FirstTrasaction(String typeTransaction,Double actualPrice, Double fee, String notes, Double totalRecived, int amount) {
        this.typeTransaction = typeTransaction;
        this.actualPrice = actualPrice;
        this.fee = fee;
        this.notes = notes;
        this.totalRecived = totalRecived;
        this.amount = amount;
    }

    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdSymbolCoin() {
        return idSymbolCoin;
    }

    public void setIdSymbolCoin(String idSymbolCoin) {
        this.idSymbolCoin = idSymbolCoin;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getTotalRecived() {
        return totalRecived;
    }

    public void setTotalRecived(Double totalRecived) {
        this.totalRecived = totalRecived;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
