package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.FirstTrasaction;
import com.co.indra.coinmarketcap.portafolio.model.entities.Portafolio;
import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id_transaction"));
        transaction.setIdAsset(rs.getInt("id_assets"));
        transaction.setTypeTransaction(rs.getString("type_transaction"));
        transaction.setDate(rs.getDate("date"));
        transaction.setActualPrice(rs.getDouble("actual_price"));
        transaction.setFee(rs.getDouble("fee"));
        transaction.setNotes(rs.getString("notes"));
        transaction.setTotalRecived(rs.getDouble("total_recived"));
        transaction.setAmount(rs.getInt("amount"));
        return transaction;
    }
}

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate template;

    public void createFirstTransaction(Long idAsset, FirstTrasaction firstTrasaction){
        template.update("INSERT INTO tbl_transactions(id_assets, type_transaction, actual_price, fee, notes, total_recived, amount) values(?,?,?,?,?,?,?)",
                idAsset, firstTrasaction.getTypeTransaction(),
                firstTrasaction.getActualPrice(), firstTrasaction.getFee(),
                firstTrasaction.getNotes(), firstTrasaction.getTotalRecived(),
                firstTrasaction.getAmount());
    }

}
