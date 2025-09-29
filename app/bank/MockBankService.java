// Mock bank service
package app.bank;

import app.transaction.Transaction;
import app.transaction.TransactionType;
import java.util.*;

public class MockBankService implements BankService {
    private Map<String, Integer> balances = new HashMap<>();
    private Map<String, String> pins = new HashMap<>();
    private Map<String, List<Transaction>> statements = new HashMap<>();

    public MockBankService() {
        balances.put("1234-5678-9012-3456", 10000);
        pins.put("1234-5678-9012-3456", "1234");
        statements.put("1234-5678-9012-3456", new ArrayList<>());
    }

    @Override
    public boolean verifyPin(String pan, String pin) {
        return pin.equals(pins.get(pan));
    }

    @Override
    public int getBalance(String pan) {
        return balances.getOrDefault(pan, 0);
    }

    @Override
    public void debit(String pan, int amount) {
        balances.put(pan, getBalance(pan) - amount);
        statements.get(pan).add(new Transaction.Builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(amount)
                .build());
    }

    @Override
    public void credit(String pan, int amount) {
        balances.put(pan, getBalance(pan) + amount);
        statements.get(pan).add(new Transaction.Builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .build());
    }

    @Override
    public boolean changePin(String pan, String newPin) {
        pins.put(pan, newPin);
        return true;
    }

    @Override
    public List<Transaction> getMiniStatement(String pan) {
        return new ArrayList<>(statements.getOrDefault(pan, List.of()));
    }
}