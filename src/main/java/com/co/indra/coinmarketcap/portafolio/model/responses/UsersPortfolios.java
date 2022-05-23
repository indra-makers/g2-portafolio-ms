package com.co.indra.coinmarketcap.portafolio.model.responses;

import java.util.List;

public class UsersPortfolios {

    private String name;

    private int balance;

    public UsersPortfolios() {
    }

    public UsersPortfolios(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
