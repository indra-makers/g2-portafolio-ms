package com.co.indra.coinmarketcap.portafolio.repositories;

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
        transaction.setId(rs.getLong("idTransaction"));
        transaction.setIdAsset(rs.getInt("idAsset"));
        transaction.setTypeTransaction(rs.getString("typeTransaction"));
        transaction.setDate(rs.getDate("date"));
        transaction.setActualPrice(rs.getDouble("actualPrice"));
        transaction.setFee(rs.getDouble("fee"));
        transaction.setNotes(rs.getString("notes"));
        transaction.setTotalRecived(rs.getDouble("totalRecived"));
        transaction.setAmount(rs.getInt("amount"));
        return transaction;
    }
}

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate template;

}
