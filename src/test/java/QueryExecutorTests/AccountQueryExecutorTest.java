package QueryExecutorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Entity.Account;
import Entity.Transaction;
import query_executor.AccountQueryExecutor;

@ExtendWith(MockitoExtension.class)
public class AccountQueryExecutorTest {

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AccountQueryExecutor accountQueryExecutor;

    @Test
    void testGetAmount() throws SQLException {
        // Arrange
        int expectedBalance = 100;
        int accountID = 1;
        Transaction transaction = new Transaction(accountID);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(eq("SELECT balance FROM Accounts where accountID=" + transaction.getAccountID())))
                .thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("balance")).thenReturn(expectedBalance);

        // Act
        int actualBalance = AccountQueryExecutor.getAmount(connection, transaction);

        // Assert
        assertEquals(expectedBalance, actualBalance);
        verify(connection, times(1)).createStatement();
        verify(statement, times(1)).executeQuery("SELECT balance FROM Accounts where accountID=" + transaction.getAccountID());
        verify(resultSet, times(1)).getInt("balance");
    }

    @Test
    void testPrintAllAccounts() throws SQLException {
        // Arrange
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM Accounts;")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("accountID")).thenReturn(1);
        when(resultSet.getInt("userID")).thenReturn(2);
        when(resultSet.getInt("balance")).thenReturn(100);
        when(resultSet.getString("currency")).thenReturn("USD");

        // Act
        accountQueryExecutor.printAllAccounts(connection);

        // Assert
        verify(connection, times(1)).createStatement();
        verify(statement, times(1)).executeQuery("SELECT * FROM Accounts;");
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getInt("accountID");
        verify(resultSet, times(1)).getInt("userID");
        verify(resultSet, times(1)).getInt("balance");
        verify(resultSet, times(1)).getString("currency");
    }

    @Test
    void testAddAccount() throws SQLException {
        // Arrange
        Account account = new Account(1, "USD");

        when(connection.prepareStatement("INSERT INTO Accounts (userID, currency) VALUES (?, ?)"))
                .thenReturn(preparedStatement);

        // Act
        accountQueryExecutor.addAccount(connection, account);

        // Assert
        verify(connection, times(1)).prepareStatement("INSERT INTO Accounts (userID, currency) VALUES (?, ?)");
        verify(preparedStatement, times(1)).setInt(1, account.getUserID());
        verify(preparedStatement, times(1)).setString(2, account.getCurrency());
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    void testDeleteAccount() throws SQLException {
        // Arrange
        int idForDelete = 1;

        when(connection.prepareStatement("DELETE FROM Accounts WHERE accountID=?"))
                .thenReturn(preparedStatement);

        // Act
        accountQueryExecutor.deleteAccount(connection, idForDelete);

        // Assert
        verify(connection, times(1)).prepareStatement("DELETE FROM Accounts WHERE accountID=?");
        verify(preparedStatement, times(1)).setInt(1, idForDelete);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteUserAccounts() throws SQLException {
        // Arrange
        int idForDelete = 1;

        when(connection.prepareStatement("DELETE FROM Accounts WHERE userID=?"))
                .thenReturn(preparedStatement);

        // Act
        accountQueryExecutor.deleteUserAccounts(connection, idForDelete);

        // Assert
        verify(connection, times(1)).prepareStatement("DELETE FROM Accounts WHERE userID=?");
        verify(preparedStatement, times(1)).setInt(1, idForDelete);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}