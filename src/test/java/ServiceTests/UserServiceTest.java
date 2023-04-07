package ServiceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.assertj.core.api.Assertions.*;

import Entity.User;
import Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    @DisplayName("Проверка создания юзера")
    void testInputUser() {
        String input = "Sasha\nminsk\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User result = UserService.inputUser();
        assertThat(result).isNotNull()
                .extracting(User::getName, User::getAddress)
                .containsExactly("Sasha", "minsk");
    }

    @Test
    @DisplayName("Тестирование проверки существующего юзера")
    void testIsUserExists() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        boolean result = UserService.isUserExists(mockConnection, 123);
        verify(mockStatement).executeQuery("SELECT userID FROM Users where userID=123");
        assertTrue(result);
    }

    @Test
    @DisplayName("Тестирование проверки на несуществующего юзера")
    void testIsUserDoesNotExist() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        boolean result = UserService.isUserExists(mockConnection, 456);
        verify(mockStatement).executeQuery("SELECT userID FROM Users where userID=456");
        assertFalse(result);
    }
}