package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Asset;
import com.co.indra.coinmarketcap.portafolio.model.entities.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class HistoryAssetsRowMapper implements RowMapper<History>{
    @Override
    public History mapRow(ResultSet rs, int rowNum) throws SQLException{
        History history = new History();
        history.setId(rs.getLong("id_assets"));
        history.setIdPortafolio(rs.getInt("id_portafolio"));
        history.setIdSymbolCoin(rs.getString("id_symbolcoin"));
        history.setQuantity(rs.getInt("quantity"));
        history.setBalanceAsset(rs.getDouble("balance"));
        history.setDollarBalance(rs.getDouble("dollar_balance"));
        history.setHistoryDate(rs.getDate("history_date"));
        return history;
    }
}

@Repository
public class HistoryAssetRepository {

    @Autowired
    private JdbcTemplate template;

    public void addHistory(Asset asset){
        template.update("INSERT INTO tbl_history_assets(id_assets, id_portafolio, id_symbolcoin, quantity, balance, dollar_balance) VALUES(?, ?, ?, ?, ?, ?)",
                asset.getId(), asset.getIdPortafolio(), asset.getIdSymbolCoin(), asset.getQuantity(), asset.getBalanceAsset(), asset.getDollarBalance());
    }

    public List<History> findHistoryByPortfolioAndAsset(String idSymbolCoin, Integer idPortafolio){
        return template.query("SELECT * FROM tbl_history_assets WHERE id_symbolcoin=? AND id_portafolio=?",
                new HistoryAssetsRowMapper(),
                idSymbolCoin,idPortafolio);
    }

}
