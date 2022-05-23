package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.util.List;

public class ListPortfolio {

    private List<UsersPortfolios> portafolios;

    private int total;

    public ListPortfolio() {
    }

    public ListPortfolio(List<UsersPortfolios> portafolios, int total) {
        this.portafolios = portafolios;
        this.total = total;
    }

    public List<UsersPortfolios> getPortafolios() {
        return portafolios;
    }

    public void setPortafolios(List<UsersPortfolios> portafolios) {
        this.portafolios = portafolios;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
