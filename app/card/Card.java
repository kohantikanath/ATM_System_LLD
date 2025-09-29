// =========================
// Filename: app/card/Card.java
// Simple Card model
// =========================
package app.card;

public class Card {
    private final String bankId;
    private final String pan;

    public Card(String bankId, String pan) {
        this.bankId = bankId;
        this.pan = pan;
    }

    public String getPan() {
        return pan;
    }

    public String getMaskedPan() {
        if (pan.length() > 4)
            return "****-****-****-" + pan.substring(pan.length() - 4);
        return pan;
    }

    public String getBankId() {
        return bankId;
    }
}