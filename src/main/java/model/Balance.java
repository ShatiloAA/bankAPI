package model;

import java.util.Objects;

public class Balance extends AbstractDTO{
    private int accountID;
    private double balance;

    public Balance() {
    }

    public Balance(int accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "accountID=" + accountID +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance1 = (Balance) o;
        return accountID == balance1.accountID &&
                Double.compare(balance1.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, balance);
    }
}
