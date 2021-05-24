package model;

import java.util.Objects;

public class Account extends AbstractDTO {
    private int ID;
    private int userId;
    private long account;
    private double balance;
    //private double increaseSize;


    public Account(int ID, int userId, long account, double balance) {
        this.ID = ID;
        this.userId = userId;
        this.account = account;
        this.balance = balance;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID=" + ID +
                ", userId=" + userId +
                ", account=" + account +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account1 = (Account) o;
        return ID == account1.ID && userId == account1.userId && account == account1.account && Double.compare(account1.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, userId, account, balance);
    }
}
