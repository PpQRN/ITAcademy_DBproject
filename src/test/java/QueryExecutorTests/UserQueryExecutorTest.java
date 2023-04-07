package QueryExecutorTests;

import Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import query_executor.UserQueryExecutor;

import java.sql.*;

@ExtendWith(MockitoExtension.class)
class UserQueryExecutorTest {

    @Mock
    private Connection connection;

    private UserQueryExecutor userQueryExecutor;

    @BeforeEach
    void setUp() {
        userQueryExecutor = new UserQueryExecutor();
    }

    @Test
    void printAllUsers() throws SQLException {
        // mock ResultSet
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("userID")).thenReturn(1);
        Mockito.when(resultSet.getString("name")).thenReturn("Sasha");
        Mockito.when(resultSet.getString("address")).thenReturn("Minsk");

        // mock Statement

        Statement statement = Mockito.mock(Statement.class);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);

        // test method
        userQueryExecutor.printAllUsers(connection);

        // verify that the query was executed and results were printed
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery("SELECT * FROM Users;");
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt("userID");
        Mockito.verify(resultSet, Mockito.times(1)).getString("name");
        Mockito.verify(resultSet, Mockito.times(1)).getString("address");
        Mockito.verify(statement, Mockito.times(1)).close();
        Mockito.verify(resultSet, Mockito.times(1)).close();
    }

    @Test
    void addUser() throws SQLException {
        // test data
        User user = new User("Minsk", "Sasha");

        // mock PreparedStatement
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);

        // test method
        userQueryExecutor.addUser(connection, user);

        // verify that the query was executed with correct parameters
        Mockito.verify(connection, Mockito.times(1)).prepareStatement("INSERT INTO Users (name, address) VALUES (?, ?)");
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(1, "Sasha");
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(2, "Minsk");
        Mockito.verify(preparedStatement, Mockito.times(1)).execute();
        Mockito.verify(preparedStatement, Mockito.times(1)).close();
    }

    @Test
    void deleteUser() throws SQLException {
        // test data
        int idForDelete = 1;

        // mock PreparedStatement
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);

        // test method
        userQueryExecutor.deleteUser(connection, idForDelete);

        // verify that the query was executed with correct parameters
        Mockito.verify(connection, Mockito.times(1)).prepareStatement("DELETE FROM Users WHERE userID=?");
        Mockito.verify(preparedStatement, Mockito.times(1)).setInt(1, 1);
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
        Mockito.verify(preparedStatement, Mockito.times(1)).close();
    }

    @Test
    void updateUser() throws SQLException {
        // test data
        int idForUpdate = 1;
        User user = new User("Minsk", "Sasha");

        // mock PreparedStatement
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);

        // test

        userQueryExecutor.updateUser(connection, idForUpdate, user);

        // verify that the query was executed with correct parameters
        Mockito.verify(connection, Mockito.times(1)).prepareStatement("UPDATE Users SET name=?, address=? WHERE userID=?");
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(1, "Sasha");
        Mockito.verify(preparedStatement, Mockito.times(1)).setString(2, "Minsk");
        Mockito.verify(preparedStatement, Mockito.times(1)).setInt(3, 1);
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
        Mockito.verify(preparedStatement, Mockito.times(1)).close();
    }
}