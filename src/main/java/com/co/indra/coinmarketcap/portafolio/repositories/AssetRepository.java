package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

class AssetRowMapper implements RowMapper<Asset> {
    @Override
    public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
        Asset asset = new Asset();
        asset.setId(rs.getLong("id_assets"));
        asset.setIdPortafolio(rs.getInt("id_portafolio"));
        asset.setIdSymbolCoin(rs.getInt("id_symbolCoin"));
        asset.setQuantity(rs.getInt("quantity"));
        asset.setBalanceAsset(rs.getDouble("balance"));
        asset.setDollarBalance(rs.getDouble("dollar_balance"));
        return asset;
    }
}

@Repository
public class AssetRepository {

    @Autowired
    private JdbcTemplate template;
}
