package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
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

    public void createAsset(Asset asset) {
        template.update("INSERT INTO tbl_assets(id_portafolio, id_symbolCoin, quantity, balance, dollar_balance) values(?,?,?,?,?)",
                asset.getIdPortafolio(), asset.getIdSymbolCoin(), asset.getQuantity(), asset.getBalanceAsset(), asset.getDollarBalance());
    }

    public List<Asset> findAssetByPortafolioAndAsset(String idSymbolCoin, Integer idPortafolio) {
        return template.query(
                "SELECT id_portafolio, id_symbolCoin, quantity, balance, dollar_balance FROM tbl_assets WHERE id_symbolCoin=? AND id_portafolio=?",
                new AssetRowMapper(),
                idSymbolCoin, idPortafolio);
    }

    public List<Asset> findAssetById(Long id){
        return template.query("SELECT id_portafolio, id_symbolCoin, quantity, balance, dollar_balance FROM public.tbl_assets WHERE id_assets=?",
               new AssetRowMapper(), id );
    }


}
