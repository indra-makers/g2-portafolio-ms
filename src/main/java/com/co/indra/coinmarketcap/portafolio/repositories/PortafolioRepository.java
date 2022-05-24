package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.ListPortfolio;
import com.co.indra.coinmarketcap.portafolio.model.responses.UsersPortfolios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class PortafolioRowMapper implements RowMapper<Portafolio> {
    @Override
    public Portafolio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Portafolio portafolio = new Portafolio();
        portafolio.setId(rs.getLong("id_portafolio"));
        portafolio.setUsername(rs.getString("username"));
        portafolio.setNamePortafolio(rs.getString("name_portafolio"));
        portafolio.setBalancePortafolio(rs.getDouble("balance_portafolio"));
        return portafolio;
    }
}

@Repository
public class PortafolioRepository {

    @Autowired
    private JdbcTemplate template;

    public void create(Portafolio portafolio){
        template.update("INSERT INTO tbl_portafolios( username,name_portafolio, balance_portafolio) values(?,?,?)",
                portafolio.getUsername(), portafolio.getNamePortafolio(), portafolio.getBalancePortafolio());
    }
    public List<Portafolio> findByNamePortafolioAndUsername(String namePortafolio, String username) {
        return template.query(
                "SELECT id_portafolio, username, name_portafolio, balance_portafolio FROM tbl_portafolios WHERE name_portafolio=? AND username=?",
                new PortafolioRowMapper() ,
                namePortafolio, username);
    }

    public List<Portafolio> findByUsername(String username) {
        return template.query(
                "SELECT id_portafolio, username, name_portafolio, balance_portafolio FROM tbl_portafolios WHERE username=?",
                new PortafolioRowMapper() ,
                username);
    }

    public List<Portafolio> findPortafolioByIdPortafolio(Integer idPortafolio) {
        return template.query(
                "SELECT id_portafolio, username, name_portafolio, balance_portafolio FROM tbl_portafolios WHERE id_portafolio=?",
                new PortafolioRowMapper(), idPortafolio);
    }

    public int getsumBalanceAsset(Integer idPortafolio){
        return template.queryForObject("SELECT sum(balance) FROM public.tbl_assets WHERE id_portafolio=?"
                , Integer.class, idPortafolio);
    }

    public void recalculateBalanceToPortfolio(Integer idPortafolio){
        template.update("UPDATE public.tbl_portafolios SET balance_portafolio=? WHERE id_portafolio=?",
                getsumBalanceAsset(idPortafolio), idPortafolio);
    }


    public void editarPortafolio(String newName, Integer idPortafolio){
        template.update("UPDATE public.tbl_portafolios SET name_portafolio=? WHERE id_portafolio=? ",
                newName, idPortafolio);
    }



    public List<UsersPortfolios> getPortfoliosByUser(String username){
        return template.query("SELECT name_portafolio, balance_portafolio FROM public.tbl_portafolios WHERE username=?",
                (rs, rn) -> new UsersPortfolios(rs.getString("name_portafolio"),
                        (rs.getInt("balance_portafolio"))), username);
    }

    public int getSumOfBalancePortfolios(String username){
        return template.queryForObject("SELECT sum(balance_portafolio) FROM public.tbl_portafolios WHERE username=?",
                Integer.class, username);
    }



    public void deletePortafolio(Long idPortafolio){
        template.update("DELETE FROM tbl_portafolios WHERE id_portafolio=?", idPortafolio);
    }



}
