package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class AssetRowMapper implements RowMapper<Asset> {
    @Override
    public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
        Asset asset = new Asset();
        asset.setId(rs.getLong("id_assets"));
        asset.setIdPortafolio(rs.getInt("id_portafolio"));
        asset.setIdSymbolCoin(rs.getString("id_symbolCoin"));
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

    public void deleteAsset(String symbolCoin, int idPortafolio ) {
        template.update("DELETE FROM tbl_assets WHERE id_symbolCoin=? and id_portafolio=?",
                symbolCoin, idPortafolio);
    }

    public List<Asset> assetFindByPortafolioAndSimbolicoin(int idPortafolio, String simbolCoin){
        return template.query(
                "SELECT id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance FROM tb_assets WHERE id_symbolcoin=? and id_portafolio=?",
                new AssetRowMapper(),
                simbolCoin, idPortafolio);
    }
}
