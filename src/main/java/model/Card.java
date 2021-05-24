package model;

import java.util.Objects;

public class Card extends AbstractDTO{

    private int userID;
    private long cardNumber;
    private int accountID;
    private boolean isActive;

    public Card() {

    }

    public Card(int userID, long cardNumber, int accountID, boolean isActive) {
        this.userID = userID;
        this.cardNumber = cardNumber;
        this.accountID = accountID;
        this.isActive = isActive;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserId() {
        return userID;
    }

    public void setUserId(int userId) {
        this.userID = userID;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Card{" +
                "userId=" + userID +
                ", cardNumber=" + cardNumber +
                ", account=" + accountID +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return userID == card.userID &&
                cardNumber == card.cardNumber &&
                accountID == card.accountID &&
                isActive == card.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, cardNumber, accountID, isActive);
    }
}
