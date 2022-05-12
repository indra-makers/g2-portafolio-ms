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
        asset.setId(rs.getLong("idAsset"));
        asset.setIdPortafolio(rs.getInt("idPortafolio"));
        asset.setIdSymbolCoin(rs.getInt("idSymbolCoin"));
        asset.setBalanceAsset(rs.getDouble("balanceAsset"));
        asset.setDollarBalance(rs.getDouble("dollarBalance"));
        return asset;
    }
}

@Repository
public class AssetRepository {

    @Autowired
    private JdbcTemplate template;
}
