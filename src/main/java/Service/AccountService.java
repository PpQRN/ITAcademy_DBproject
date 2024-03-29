package Service;

import Entity.Account;
import Entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AccountService {
    private static final ArrayList<String> currencies =
            new ArrayList<>(Arrays.asList("USD", "EUR", "GBP", "JPY", "RUB"));

    public static Account inputAccount(Connection connection) throws SQLException {
        Account account = new Account();
        setUserID(connection, account);
        setCurrency(connection, account, account.getUserID());
        return account;
    }

    public static void setUserID(Connection connection, Account account) throws SQLException {
        int userID;
        while (true) {
            System.out.println("Enter the id of the user you want to create an account for: ");
            try {
                userID = Integer.parseInt(new Scanner(System.in).nextLine());
                if (!UserService.isUserExists(connection, userID)) {
                    System.out.println("Sorry, no such user, try again");
                    continue;
                }
                account.setUserID(userID);
                return;
            } catch (NumberFormatException e) {
                System.out.println("ID must be of number format and not empty, try again");
            }
        }
    }

    public static void setCurrency(Connection connection, Account account, int userID) throws SQLException {
        String currency;
        while (true) {
            System.out.println("Enter currency");
            currency = new Scanner(System.in).nextLine().toUpperCase();
            if (!currencies.contains(currency)) {
                System.out.println("Sorry, there are no such currency");
                continue;
            } else if (isAccountWithThatCurrencyExists(connection, userID, currency)) {
                System.out.println("Sorry, account with this currency already exists");
                continue;
            }
            account.setCurrency(currency);
            return;
        }
    }

    public static boolean isAccountWithThatCurrencyExists(Connection connection, int userID, String currency) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Accounts WHERE userID=? AND currency=?");
        statement.setInt(1, userID);
        statement.setString(2, currency);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public static boolean isAccountExists(Connection connection, int accountID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT accountID FROM Accounts where accountID=" + accountID);
        return resultSet.next();
    }

    public static void isDepositAvailable(Connection connection, Transaction transaction) throws SQLException {
        Statement statement = connection.createStatement();
        int accountID = transaction.getAccountID();
        String sql = "SELECT balance FROM Accounts WHERE accountID=" + accountID;
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            throw new IllegalArgumentException(String.format("No accounts with id %s", accountID));
        }
        if ((transaction.getAmount() + resultSet.getInt("balance")) > 2000000000) {
            throw new IllegalArgumentException("Sorry, you can't store more than 100 000 000, try to make a smaller deposit");
        }
    }

    public static void isWithdrawalAvailable(Connection connection, Transaction transaction) throws SQLException {
        Statement statement = connection.createStatement();
        int accountID = transaction.getAccountID();
        String sql = "SELECT balance FROM Accounts WHERE accountID=" + accountID;
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            throw new IllegalArgumentException(String.format("No accounts with id %s", accountID));
        }
        if ((resultSet.getInt("balance") - transaction.getAmount()) < 0) {
            throw new IllegalArgumentException("Sorry, you don't have enough money, try to withdrawal less money");
        }
    }
}

