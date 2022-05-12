package com.co.indra.coinmarketcap.portafolio.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "tb_portafolio")
public class Portafolio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPortafolio")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name_portafolio")
    private String name;

    @Column(name = "balancePortafolio")
    private Double balancePortafolio;

    public Portafolio() {
    }

    public Portafolio(Long id, String username, String name, Double balancePortafolio) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.balancePortafolio = balancePortafolio;
    }

    public Portafolio(String username, String name, Double balancePortafolio) {
        this.username = username;
        this.name = name;
        this.balancePortafolio = balancePortafolio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
