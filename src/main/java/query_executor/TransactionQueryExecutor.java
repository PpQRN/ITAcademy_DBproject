package query_executor;

import Entity.Transaction;
import Service.AccountService;

import java.sql.*;

public class TransactionQueryExecutor {


    public void depositFunds(Connection connection, Transaction transaction) throws SQLException {
        AccountService.isDepositAvailable(connection, transaction);
        int existingAmount = AccountQueryExecutor.getAmount(connection, transaction);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Accounts SET balance=?");
        preparedStatement.setInt(1, transaction.getAmount() + existingAmount);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        addDepositTransaction(connection, transaction);
    }


    public void withdrawalFunds(Connection connection, Transaction transaction) throws SQLException {
        AccountService.isWithdrawalAvailable(connection, transaction);
        int existingAmount = AccountQueryExecutor.getAmount(connection, transaction);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Accounts SET balance=?");
        preparedStatement.setInt(1, existingAmount - transaction.getAmount());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        addWithdrawalTransaction(connection, transaction);
    }

    public void addDepositTransaction(Connection connection, Transaction transaction) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Transactions (accountID, amount) VALUES (?, ?)");
        preparedStatement.setInt(1, transaction.getAccountID());
        preparedStatement.setInt(2, transaction.getAmount());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addWithdrawalTransaction(Connection connection, Transaction transaction) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Transactions (accountID, amount) VALUES (?, ?)");
        preparedStatement.setInt(1, transaction.getAccountID());
        preparedStatement.setInt(2, transaction.getAmount() * -1);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteUser(Connection connection, int idForDelete) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Users WHERE userID=?");
        preparedStatement.setInt(1, idForDelete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
