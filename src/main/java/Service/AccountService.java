package Service;

import Entity.Account;

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

    public static void isTransactionAvailable(Connection connection, int accountID, int amount, String transactionType) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT balance FROM Accounts where accountID=" + accountID);
        if (transactionType.equals("deposit")) {
            if ((amount + resultSet.getInt("balance")) > 100000000) {
                throw new IllegalArgumentException("Sorry, you can't store more than 100 000 000, try to make a smaller deposit");
            }
        } else {
            if ((resultSet.getInt("balance") - amount) < 0) {
                throw new IllegalArgumentException("Sorry, you don't have enough money, try to withdrawal less money");
            }
        }
    }
}
