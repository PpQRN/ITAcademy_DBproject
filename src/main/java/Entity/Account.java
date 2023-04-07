package Entity;

import java.util.Objects;

public class Account {

    private int accountID;
    private int userID;
    private int balance;
    private String currency;

    public Account() {
    }

    public Account(int accountID, int userID, int balance, String currency) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
        this.currency = currency;
    }

    public Account(int userID, String currency) {
        this.userID = userID;
        this.currency = currency;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountID == account.accountID && userID == account.userID && balance == account.balance && Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, userID, balance, currency);
    }
}
