package query_executor;

import Entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionQueryExecutor {


    public void depositFunds(Connection connection, Transaction transaction) throws SQLException {
        int existingAmount = AccountQueryExecutor.getAmount(connection, transaction);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Accounts SET balance=?");
        preparedStatement.setInt(1, transaction.getAmount()+existingAmount);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public void withdrawalFunds(Connection connection, Transaction transaction) throws SQLException {
        int existingAmount = AccountQueryExecutor.getAmount(connection, transaction);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Accounts SET balance=?");
        preparedStatement.setInt(1, existingAmount-transaction.getAmount());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
