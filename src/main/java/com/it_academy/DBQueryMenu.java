package com.it_academy;

import Service.AccountService;
import Service.TransactionService;
import Service.UserService;
import query_executor.AccountQueryExecutor;
import query_executor.TransactionQueryExecutor;
import query_executor.UserQueryExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DBQueryMenu {

    private static String action;

    public static void menu(Connection connection) throws SQLException {
        do {
            printMenuGeneral();
            action = new Scanner(System.in).nextLine();
            switch (action) {
                case "1" -> menuUser(connection);
                case "2" -> menuAccount(connection);
                case "3" -> menuTransactions(connection);
//                case "4" -> {}
//                default -> System.out.println("No such option");
            }
        } while (!"4".equals(action));
        connection.close();
    }

    public static void menuUser(Connection connection) throws SQLException {
        do {
            printMenuUser();
            action = new Scanner(System.in).nextLine();
            UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
            switch (action) {
                case "1" -> userQueryExecutor.printAllUsers(connection);
                case "2" -> userQueryExecutor.addUser(connection, UserService.inputUser());
                case "3" -> {
                    System.out.println("Enter id for delete");
                    userQueryExecutor.printAllUsers(connection);
                    int idForDelete = new Scanner(System.in).nextInt();
                    userQueryExecutor.deleteUser(connection, idForDelete);
                }
                case "4" -> {
                    System.out.println("Enter id for update");
                    userQueryExecutor.printAllUsers(connection);
                    int idForUpdate = new Scanner(System.in).nextInt();
                    userQueryExecutor.updateUser(connection, idForUpdate, UserService.inputUser());
                }
                case "5" -> {
                }
                default -> System.out.println("No such option");
            }
        } while (!"5".equals(action));
        menu(connection);
    }

    public static void menuAccount(Connection connection) throws SQLException {
        do {
            printMenuAccount();
            action = new Scanner(System.in).nextLine();
            AccountQueryExecutor accountQueryExecutor = new AccountQueryExecutor();
            switch (action) {
                case "1" -> accountQueryExecutor.printAllAccounts(connection);
                case "2" -> accountQueryExecutor.addAccount(connection, AccountService.inputAccount(connection));
                case "3" -> {
                    System.out.println("Enter accountID for delete");
                    accountQueryExecutor.printAllAccounts(connection);
                    int idForDelete = new Scanner(System.in).nextInt();
                    accountQueryExecutor.deleteAccount(connection, idForDelete);
                }
                case "5" -> {
                }
                default -> System.out.println("No such option");
            }
        } while (!"4".equals(action));
        menu(connection);
    }

    public static void menuTransactions(Connection connection) throws SQLException {
        do {
            printMenuTransactions();
            action = new Scanner(System.in).nextLine();
            TransactionQueryExecutor transactionQueryExecutor = new TransactionQueryExecutor();
            switch (action) {
                case "1" -> transactionQueryExecutor.depositFunds(connection,
                        TransactionService.inputTransaction(connection, "deposit"));
                case "2" -> transactionQueryExecutor.withdrawalFunds(connection,
                        TransactionService.inputTransaction(connection, "withdrawal"));
                default -> System.out.println("No such option");
            }
        } while (!"3".equals(action));
        menu(connection);
    }

    private static void printMenuGeneral() {
        System.out.println("1 - add/delete/update Users");
        System.out.println("2 - add/delete/update Accounts");
        System.out.println("3 - deposit/withdrawal of funds from the account");
        System.out.println("4 - exit");
    }

    private static void printMenuUser() {
        System.out.println("1 - show all users");
        System.out.println("2 - add new user");
        System.out.println("3 - delete user");
        System.out.println("4 - update user data");
        System.out.println("5 - back");
    }

    private static void printMenuAccount() {
        System.out.println("1 - show all accounts");
        System.out.println("2 - add account");
        System.out.println("3 - delete account");
        System.out.println("4 - back");
    }

    private static void printMenuTransactions() {
        System.out.println("1 - deposit funds");
        System.out.println("2 - withdrawal funds");
        System.out.println("3 - back");
    }
}
