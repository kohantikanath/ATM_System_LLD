// BankService interface
package app.bank;

import app.transaction.Transaction;
import java.util.*;

public interface BankService {
    boolean verifyPin(String pan, String pin);

    int getBalance(String pan);

    void debit(String pan, int amount);

    void credit(String pan, int amount);

    boolean changePin(String pan, String newPin);

    List<Transaction> getMiniStatement(String pan);
}