package ServiceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.*;

import Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Entity.Transaction;

public class AccountServiceTest {
    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = mock(Connection.class);
    }

    @Test
    void testIsAccountWithThatCurrencyExists() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        boolean result = AccountService.isAccountWithThatCurrencyExists(connection, 123, "USD");
        verify(preparedStatement).setInt(1, 123);
        verify(preparedStatement).setString(2, "USD");
        verify(preparedStatement).executeQuery();
        assertTrue(result);
    }

    @Test
    void testIsAccountExists() throws SQLException {
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        boolean result = AccountService.isAccountExists(connection, 123);
        verify(statement).executeQuery("SELECT accountID FROM Accounts where accountID=123");
        assertTrue(result);
    }

    @Test
    void testIsDepositAvailable() throws SQLException {
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        Transaction transaction = new Transaction();
        transaction.setAccountID(123);
        transaction.setAmount(10);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.getInt("balance")).thenReturn(2000000000);
        assertThrows(IllegalArgumentException.class, () -> AccountService.isDepositAvailable(connection, transaction));
    }

    @Test
    void testIsWithdrawalAvailable() throws SQLException {
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        Transaction transaction = new Transaction();
        transaction.setAccountID(123);
        transaction.setAmount(600000000);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.getInt("balance")).thenReturn(0);
        assertThrows(IllegalArgumentException.class, () -> AccountService.isWithdrawalAvailable(connection, transaction));
    }
}