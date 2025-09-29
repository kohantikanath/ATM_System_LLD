package app.components;

public interface Printer {
    void printReceipt(app.transaction.Transaction tx);

    void printMiniStatement(java.util.List<app.transaction.Transaction> mini);
}