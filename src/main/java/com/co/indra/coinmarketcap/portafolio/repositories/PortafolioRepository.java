package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

class PortafolioRowMapper implements RowMapper<Portafolio> {
    @Override
    public Portafolio mapRow(ResultSet rs, int rowNum) throws SQLException {
        Portafolio portafolio = new Portafolio();
        portafolio.setId(rs.getLong("id_portafolio"));
        portafolio.setMailUser(rs.getString("mail_user"));
        portafolio.setName(rs.getString("name_portafolio"));
        portafolio.setBalancePortafolio(rs.getDouble("balance_portafolio"));
        return portafolio;
    }
}

@Repository
public class PortafolioRepository {

    @Autowired
    private JdbcTemplate template;

}
