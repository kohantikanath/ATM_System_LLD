package app.tx;

import app.transaction.Transaction;

public interface TransactionListener {
    void onTransactionRecorded(Transaction tx);
}