package QueryExecutorTests;

import Entity.Transaction;
import Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import query_executor.TransactionQueryExecutor;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void testDepositFunds() throws SQLException {
        // Arrange
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Transaction transaction = new Transaction(1, 100);
        //Statement statement = Mockito.mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("balance")).thenReturn(200);

        // Act
        executor.depositFunds(mockConnection, transaction);

        // Assert
        verify(mockConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement, mockResultSet);
    }

    @Test
    void testWithdrawalFunds() throws SQLException {
        // Arrange
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Transaction transaction = new Transaction(1, 100);
        //Statement statement = Mockito.mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("balance")).thenReturn(200);

        // Act
        executor.withdrawalFunds(mockConnection, transaction);

        // Assert
        verify(mockConnection, times(1)).createStatement();
        verify(mockPreparedStatement, times(1)).executeQuery(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement, mockPreparedStatement, mockResultSet);
    }

    @Test
    void testAddDepositTransaction() throws SQLException {
        // Arrange
        Transaction transaction = new Transaction(1, 100);

        // Act
        executor.addDepositTransaction(mockConnection, transaction);

        // Assert
        verify(mockConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }

    @Test
    void testAddWithdrawalTransaction() throws SQLException {
        // Arrange
        Transaction transaction = new Transaction(1, 100);

        // Act
        executor.addWithdrawalTransaction(mockConnection, transaction);

        // Assert
        verify(mockConnection, times(1)).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).execute();
        verify(mockPreparedStatement, times(1)).close();
        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }
}


