package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
        portafolio.setMailUser(rs.getString("mail_user"));
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
        template.update("INSERT INTO tbl_portafolios( mail_user,name_portafolio, balance_portafolio) values(?,?,?)",
                portafolio.getMailUser(), portafolio.getNamePortafolio(), portafolio.getBalancePortafolio());
    }
    public List<Portafolio> findByNamePortafolioAndMailUser(String namePortafolio, String mailUser) {
        return template.query(
                "SELECT id_portafolio, mail_user, name_portafolio, balance_portafolio FROM tbl_portafolios WHERE name_portafolio=? AND mail_user=?",
                new PortafolioRowMapper() ,
                namePortafolio, mailUser);
    }

    public List<Portafolio> findByMailUser(String mailUser) {
        return template.query(
                "SELECT id_portafolio, mail_user, name_portafolio, balance_portafolio FROM tbl_portafolios WHERE mail_user=?",
                new PortafolioRowMapper() ,
                mailUser);
    }


}
