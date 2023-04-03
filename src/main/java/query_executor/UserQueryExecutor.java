package query_executor;

import Entity.User;

import java.sql.*;

public class UserQueryExecutor {

    public void printAllUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users;");
        while (result.next()) {
            System.out.println("userID: " + result.getInt("userID"));
            System.out.println("name:" + result.getString("name"));
            System.out.println("address: " + result.getString("address"));
        }
        statement.close();
        result.close();
    }

    public void addUser(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Users (name, address) VALUES (?, ?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getAddress());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteUser(Connection connection, int idForDelete) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Users WHERE userID=?");
        preparedStatement.setInt(1, idForDelete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateUser(Connection connection, int idForUpdate, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Users SET name=?, address=? WHERE id=?");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getAddress());
        preparedStatement.setInt(4, idForUpdate);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
