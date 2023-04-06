package Service;

import Entity.Transaction;
import query_executor.AccountQueryExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionService {

    public static Transaction inputTransaction(Connection connection) throws SQLException {
        Transaction transaction = new Transaction();
        setAccountID(connection, transaction);
        setAmount(transaction);
        return transaction;
    }

    public static void setAccountID(Connection connection, Transaction transaction) throws SQLException {
        int accountID;
        while (true) {
            try {
                System.out.println("What account you want to interact with");
                new AccountQueryExecutor().printAllAccounts(connection);
                accountID = Integer.parseInt(new Scanner(System.in).nextLine());
                if (!AccountService.isAccountExists(connection, accountID)) {
                    System.out.println("Sorry, there is no such account, try again");
                    continue;
                }
                transaction.setAccountID(accountID);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Input error, Enter valid Account");
            }
        }
    }

    public static void setAmount(Transaction transaction) {
        int amount;
        while (true) {
            try {
                System.out.println("Enter amount (Positive numbers, not more than 100 000 000)");
                amount = Integer.parseInt(new Scanner(System.in).nextLine());
                if (amount < 0 || amount > 100000000) {
                    System.out.println("Illegal sum. Enter positive numbers, not more than 100 000 000 ");
                    continue;
                }
                transaction.setAmount(amount);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Input error, Enter valid sum of transaction");
            }
        }
    }
}
