package QueryExecutorTests;

import Entity.Account;
import Entity.Transaction;
import Entity.User;
import TestUtils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query_executor.AccountQueryExecutor;
import query_executor.TransactionQueryExecutor;
import query_executor.UserQueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionQueryExecutorTest {

    private TransactionQueryExecutor executor;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        executor = new TransactionQueryExecutor();
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.execute()).thenReturn(true);
        when(mockPreparedStatement.getResultSet()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
    }

    @Test
    public void testDepositFunds() throws SQLException, ClassNotFoundException {
        TransactionQueryExecutor executor = new TransactionQueryExecutor();
        ResultSet rs;
        Connection connection = TestUtils.getConnection();
        TestUtils.createTables(connection);
        AccountQueryExecutor accountExecutor = new AccountQueryExecutor();
        User user = new User("Mogilev", "Alex");
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.addUser(connection, user);
        Account account = new Account(1, 1, 0, "USD");
        accountExecutor.addAccount(connection, account);
        Transaction transaction = new Transaction(1, 50);
        executor.depositFunds(connection, transaction);
        rs = connection.createStatement().executeQuery("SELECT * FROM Accounts WHERE accountID = 1");
        rs.next();
        int balance = rs.getInt("balance");
        assertEquals(50, balance);
        rs = connection.createStatement().executeQuery("SELECT * FROM Transactions");
        rs.next();
        int accountID = rs.getInt("accountID");
        int amount = rs.getInt("amount");
        assertEquals(1, accountID);
        assertEquals(50, amount);
        rs.close();
        connection.close();
    }

    @Test
    public void testWithdrawalFunds() throws SQLException, ClassNotFoundException {
        TransactionQueryExecutor executor = new TransactionQueryExecutor();
        ResultSet rs;
        Connection connection = TestUtils.getConnection();
        TestUtils.createTables(connection);
        AccountQueryExecutor accountExecutor = new AccountQueryExecutor();
        User user = new User("Mogilev", "Alex");
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.addUser(connection, user);
        Account account = new Account(1, 1, 60, "USD");
        accountExecutor.addAccount(connection, account);
        Transaction transaction = new Transaction(1, 2);
        executor.withdrawalFunds(connection, transaction);
        rs = connection.createStatement().executeQuery("SELECT * FROM Accounts WHERE accountID = 1");
        rs.next();
        int balance = rs.getInt("balance");
        assertEquals(58, balance);
        rs = connection.createStatement().executeQuery("SELECT * FROM Transactions");
        rs.next();
        int accountID = rs.getInt("accountID");
        int amount = rs.getInt("amount");
        assertEquals(1, accountID);
        assertEquals(-2, amount);
        rs.close();
        connection.close();
    }

    @Test
    void testAddDepositTransaction() throws SQLException {
        Transaction transaction = new Transaction(1, 100);
        executor.addDepositTransaction(mockConnection, transaction);
        verify(mockConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }

    @Test
    void testAddWithdrawalTransaction() throws SQLException {
        Transaction transaction = new Transaction(1, 100);
        executor.addWithdrawalTransaction(mockConnection, transaction);
        verify(mockConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }
}