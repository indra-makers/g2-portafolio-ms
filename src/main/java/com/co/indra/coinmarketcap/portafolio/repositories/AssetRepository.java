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

class FirstAssetRowMapper implements RowMapper<Asset> {
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

    public void updateQuantityAsset(Long idAsset, int quantityTransaction){
        template.update("UPDATE public.tbl_assets SET quantity=?  WHERE id_assets=?",
                quantityTransaction, idAsset);
    }

    public List<Asset> getIdAssetByPortafolioAndIdSymbolCoin(String idSymbolCoin, Integer idPortafolio) {
        return template.query(
                "SELECT id_assets, id_portafolio, id_symbolCoin, quantity, balance, dollar_balance FROM tbl_assets WHERE id_symbolCoin=? AND id_portafolio=?",
                new FirstAssetRowMapper(),
                idSymbolCoin, idPortafolio);
    }

    public void deleteAsset(String symbolCoin, int idPortafolio ) {
        template.update("DELETE FROM tbl_assets WHERE id_symbolCoin=? and id_portafolio=?",
                symbolCoin, idPortafolio);
    }

    public List<Asset> assetFindByIdPortafolio(int idPortafolio){
        return template.query(
                "SELECT id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance FROM tbl_assets WHERE id_portafolio=?",
                new AssetRowMapper(),
                idPortafolio);
    }

    public List<Asset> assetFindByPortafolioAndSimbolicoin(int idPortafolio, String simbolCoin){
        return template.query(
                "SELECT id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance FROM tbl_assets WHERE id_symbolcoin=? and id_portafolio=?",
                new AssetRowMapper(),
                simbolCoin, idPortafolio);
    }

    public long findAvgBuyPriceByAsset(long idAsset){
        return template.queryForObject("SELECT avg_buy_price FROM public.tbl_assets WHERE id_assets=?"
                , Long.class, idAsset);
    }

    public long getAvgBuyPriceAsset(long idAsset){
        return template.queryForObject("SELECT avg(actual_price) FROM public.tbl_transactions WHERE type_transaction ='buy' AND id_assets=?"
                , Long.class, idAsset);
    }

    public void recalculateAvgBuyPriceToAsset(Long idAsset){
        template.update("UPDATE public.tbl_assets SET avg_buy_price=? WHERE id_assets=?",
                getAvgBuyPriceAsset(idAsset), idAsset);
    }

}
