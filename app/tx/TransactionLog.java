// =========================
// Filename: app/tx/TransactionLog.java
// Observer pattern: notify listeners when transaction recorded
// =========================
package app.tx;

import app.transaction.Transaction;
import java.util.*;

public class TransactionLog {
    private List<TransactionListener> listeners = new ArrayList<>();
    private List<Transaction> history = new ArrayList<>();

    public void addListener(TransactionListener l) {
        listeners.add(l);
    }

    public void record(Transaction tx) {
        history.add(tx);
        listeners.forEach(l -> l.onTransactionRecorded(tx));
    }

    public List<Transaction> getLastN(int n) {
        int from = Math.max(0, history.size() - n);
        return new ArrayList<>(history.subList(from, history.size()));
    }
}