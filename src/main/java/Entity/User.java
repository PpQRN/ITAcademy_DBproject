package Entity;

import java.util.Objects;

public class User {

    private int userID;
    private String address;
    private String name;

    public User() {
    }

    public User( String address, String name) {
        this.address = address;
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && Objects.equals(address, user.address) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, address, name);
    }
}
