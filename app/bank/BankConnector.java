// =========================
// Filename: app/bank/BankConnector.java
// Adapter + Proxy to bank service to make ATM extensible to many banks
// =========================
package app.bank;

import app.card.Card;
import java.util.*;

public class BankConnector {
    private final BankService bank;

    public BankConnector(BankService bank) {
        this.bank = bank;
    }

    public boolean verifyPin(Card c, String pin) {
        return bank.verifyPin(c.getPan(), pin);
    }

    public boolean hasSufficientFunds(Card c, int amount) {
        return bank.getBalance(c.getPan()) >= amount;
    }

    public void debit(Card c, int amount) {
        bank.debit(c.getPan(), amount);
    }

    public void credit(Card c, int amount) {
        bank.credit(c.getPan(), amount);
    }

    public boolean changePin(Card c, String newPin) {
        return bank.changePin(c.getPan(), newPin);
    }

    public java.util.List<app.transaction.Transaction> getMiniStatement(Card c) {
        return bank.getMiniStatement(c.getPan());
    }
}