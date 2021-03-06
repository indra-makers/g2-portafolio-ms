package com.co.indra.coinmarketcap.portafolio.repositories;

import com.co.indra.coinmarketcap.portafolio.model.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        transaction.setQuantity(rs.getInt("quantity"));
        transaction.setTotalRecived(rs.getDouble("total_recived"));
        transaction.setAmount(rs.getInt("amount"));
        return transaction;
    }
}

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate template;


    public void createTransaction (Transaction transaction, Long id) {
        template.update("INSERT INTO public.tbl_transactions (id_assets, type_transaction, date, actual_price, fee, notes, quantity, total_recived, amount) VALUES (?,?,?,?,?,?,?,?,?)",
                id, transaction.getTypeTransaction(), transaction.getDate(), transaction.getActualPrice(), transaction.getFee(), transaction.getNotes(), transaction.getQuantity(), transaction.getTotalRecived(), transaction.getAmount());
    }

    public List<Transaction> findTransactionByIdAsset(int idAsset) {
        return template.query(
                "SELECT id_transaction, id_assets, type_transaction, date, actual_price, fee, notes, total_recived, amount, quantity FROM tbl_transactions WHERE id_assets=?",
                new TransactionRowMapper(), idAsset);
    }

    public List<Transaction>  findTransactionById(Long idTransaction) {
        return template.query(
                "SELECT id_transaction, id_assets, type_transaction, date, actual_price, fee, notes, total_recived, amount, quantity FROM tbl_transactions WHERE id_transaction=?",
                new TransactionRowMapper(), idTransaction);
    }

    public void editarTransaction(Transaction transaction, Long idTransaction){
        template.update("UPDATE public.tbl_transactions SET date=?, actual_price=?, fee=?, notes=?, total_recived=?, amount=?, quantity=? WHERE id_transaction=?",
                transaction.getDate(), transaction.getActualPrice(), transaction.getFee(), transaction.getNotes(), transaction.getTotalRecived(), transaction.getAmount(), transaction.getQuantity(), idTransaction);
    }

}
