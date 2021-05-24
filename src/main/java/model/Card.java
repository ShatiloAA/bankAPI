package model;

import java.util.Objects;

public class Card extends AbstractDTO{

    private int cardID;
    private long cardNumber;
    private int accountID;
    private boolean isActive;

    public Card() {

    }

    public Card(int cardID, long cardNumber, int accountID, boolean isActive) {
        this.cardID = cardID;
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
        return cardID;
    }

    public void setUserId(int userId) {
        this.cardID = cardID;
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
                "userId=" + cardID +
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
        return cardID == card.cardID &&
                cardNumber == card.cardNumber &&
                accountID == card.accountID &&
                isActive == card.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardID, cardNumber, accountID, isActive);
    }
}
