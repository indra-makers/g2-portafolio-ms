package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction")
    private Long id;

    @Column(name = "idAsset")
    private Integer idAsset;

    @Column(name = "typeTransaction")
    private String typeTransaction;

    @Column(name = "date")
    private Date date;

    @Column(name = "actualPrice")
    private Double actualPrice;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "notes")
    private String notes;

    @Column(name = "totalRecived")
    private Double totalRecived;

    @Column(name = "amount")
    private int amount;

    public Transaction() {
    }

    public Transaction(Long id, Integer idAsset, String typeTransaction, Date date, Double actualPrice, Double fee, String notes, Double totalRecived, int amount) {
        this.id = id;
        this.idAsset = idAsset;
        this.typeTransaction = typeTransaction;
        this.date = date;
        this.actualPrice = actualPrice;
        this.fee = fee;
        this.notes = notes;
        this.totalRecived = totalRecived;
        this.amount = amount;
    }

    public Transaction(Integer idAsset, String typeTransaction, Date date, Double actualPrice, Double fee, String notes, Double totalRecived, int amount) {
        this.idAsset = idAsset;
        this.typeTransaction = typeTransaction;
        this.date = date;
        this.actualPrice = actualPrice;
        this.fee = fee;
        this.notes = notes;
        this.totalRecived = totalRecived;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(Integer idAsset) {
        this.idAsset = idAsset;
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
