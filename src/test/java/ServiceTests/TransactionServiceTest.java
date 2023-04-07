package ServiceTests;

import Entity.Account;
import Entity.Transaction;
import Entity.User;
import TestUtils.TestUtils;
import Service.TransactionService;
import org.junit.jupiter.api.Test;
import query_executor.AccountQueryExecutor;
import query_executor.UserQueryExecutor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionServiceTest {

    @Test
    public void testSetAccountID() throws SQLException, ClassNotFoundException {
        Connection connection = TestUtils.getConnection();
        TestUtils.createTables(connection);
        AccountQueryExecutor accountExecutor = new AccountQueryExecutor();
        User user = new User("Mogilev", "Alex");
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.addUser(connection, user);
        Account account = new Account(1, 1, 0, "USD");
        accountExecutor.addAccount(connection, account);
        Transaction transaction = new Transaction();
        InputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        TransactionService.setAccountID(connection, transaction);
        assertEquals(1, transaction.getAccountID());
        connection.close();
    }

    @Test
    public void testSetAmount() throws SQLException, ClassNotFoundException {
        Connection connection = TestUtils.getConnection();
        TestUtils.createTables(connection);
        AccountQueryExecutor accountExecutor = new AccountQueryExecutor();
        User user = new User("Mogilev", "Alex");
        UserQueryExecutor userQueryExecutor = new UserQueryExecutor();
        userQueryExecutor.addUser(connection, user);
        Account account = new Account(1, 1, 0, "USD");
        accountExecutor.addAccount(connection, account);
        Transaction transaction = new Transaction();
        InputStream in = new ByteArrayInputStream("50".getBytes());
        System.setIn(in);
        TransactionService.setAmount(transaction);
        assertEquals(50, transaction.getAmount());
        connection.close();
    }


}
