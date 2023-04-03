package Service;

import Entity.User;

import java.sql.*;
import java.util.Scanner;

public class UserService {

    public static User inputUser() {
        User user = new User();
        String address;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter User name: ");
        user.setName(sc.nextLine());
        System.out.println("Enter User address (optional field): ");
        address = sc.nextLine();
        if (address.equals("")) {
            return user;
        }
        user.setAddress(address);
        return user;
    }

    public static boolean isUserExists(Connection connection, int userID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userID FROM Users where userID=" + userID);
        return resultSet.next();
    }
}
