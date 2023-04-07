package TestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUtils {

    private static final String JDBC_URL = "jdbc:h2:mem:test";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    public static void createTables(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE Users (
                  userID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
                  name VARCHAR(50),
                  address VARCHAR(225)
                );
                """);
        statement.execute("""
                CREATE TABLE Accounts (
                  accountID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
                  userID INTEGER(10),
                  balance INTEGER(15) CHECK (balance <= 2000000000),
                  currency VARCHAR(10),
                  FOREIGN KEY (userID) REFERENCES Users(userID)
                );
                """);
        statement.execute("""
                CREATE TABLE Transactions (
                  transactionID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
                  accountID INTEGER(10),
                  amount INTEGER(15) CHECK (amount <= 100000000),
                  FOREIGN KEY (accountID) REFERENCES Accounts(accountID)
                );
                """);
        statement.close();
    }

    public static void dropTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS Transactions");
        statement.execute("DROP TABLE IF EXISTS Accounts");
        statement.execute("DROP TABLE IF EXISTS Users");
        statement.close();
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
