// Printer with observer capability
package app.components.impl;

import app.components.Printer;
import app.transaction.Transaction;
import app.tx.TransactionListener;
import java.util.*;

public class SimplePrinter implements Printer, TransactionListener {
    public void printReceipt(Transaction tx) {
        System.out.println("--- RECEIPT ---");
        System.out.println(tx);
        System.out.println("--- END ---");
    }

    public void printMiniStatement(List<Transaction> mini) {
        System.out.println("--- MINI STATEMENT ---");
        for (Transaction t : mini)
            System.out.println(t);
        System.out.println("--- END MINI ---");
    }

    @Override
    public void onTransactionRecorded(Transaction tx) {
        printReceipt(tx);
    }
}