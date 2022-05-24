package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.io.Serializable;

public class PortafoliosDistribution{

    private String IdSymbolCoin;
    private Double percent;


    public PortafoliosDistribution(String id_symbolCoin, Double percent) {
        this.IdSymbolCoin = id_symbolCoin;
        this.percent = percent;
    }

    public PortafoliosDistribution() {
    }

    public String getIdSymbolCoin() {
        return IdSymbolCoin;
    }

    public void setIdSymbolCoin(String idSymbolCoin) {
        IdSymbolCoin = idSymbolCoin;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
