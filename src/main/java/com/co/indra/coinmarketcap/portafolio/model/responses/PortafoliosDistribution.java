package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.io.Serializable;

public class PortafoliosDistribution{

    private String id_symbolCoin;
    private Double average;


    public PortafoliosDistribution(String id_symbolCoin, Double average) {
        this.id_symbolCoin = id_symbolCoin;
        this.average = average;
    }

    public PortafoliosDistribution() {
    }

    public String getId_symbolCoin() {
        return id_symbolCoin;
    }

    public void setId_symbolCoin(String id_symbolCoin) {
        this.id_symbolCoin = id_symbolCoin;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
