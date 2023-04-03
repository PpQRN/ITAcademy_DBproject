package Entity;

import java.util.Objects;

public class Transaction {

    private int transactionID;
    private int amount;
    private int accountID;

    public Transaction(int transactionID, int amount, int accountID) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.accountID = accountID;
    }

    public Transaction() {
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID == that.transactionID && amount == that.amount && accountID == that.accountID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, amount, accountID);
    }
}
