package query_executor;

import Entity.Account;
import Entity.Transaction;

import java.sql.*;

public class AccountQueryExecutor {

    public static int getAmount(Connection connection, Transaction transaction) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT balance FROM Accounts where accountID="
                +transaction.getAccountID());
        if (result.next()) {
            return result.getInt("balance");
        } else {
            return 0;
        }
    }

    public void printAllAccounts(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Accounts;");
        while (result.next()) {
            System.out.println("accountID: " + result.getInt("accountID"));
            System.out.println("userID:" + result.getInt("userID"));
            System.out.println("balance: " + result.getInt("balance"));
            System.out.println("currency: " + result.getString("currency"));
        }
        statement.close();
        result.close();
    }

    public void addAccount(Connection connection, Account account) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Accounts (userID, currency, balance) VALUES (?, ?, ?)");
        preparedStatement.setInt(1, account.getUserID());
        preparedStatement.setString(2, account.getCurrency());
        preparedStatement.setInt(3, account.getBalance());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteAccount(Connection connection, int idForDelete) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Accounts WHERE accountID=?");
        preparedStatement.setInt(1, idForDelete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteUserAccounts(Connection connection, int idForDelete) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Accounts WHERE userID=?");
        preparedStatement.setInt(1, idForDelete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
