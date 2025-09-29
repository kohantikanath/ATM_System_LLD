// =========================
// Filename: app/transaction/Transaction.java
// Builder + Prototype (clone) for transaction
// =========================
package app.transaction;

import java.time.Instant;

public class Transaction implements Cloneable {
    private final TransactionType type;
    private final int amount;
    private final String details;
    private final Instant timestamp;

    private Transaction(Builder b) {
        this.type = b.type;
        this.amount = b.amount;
        this.details = b.details;
        this.timestamp = Instant.now();
    }

    public static class Builder {
        private TransactionType type;
        private int amount;
        private String details = "";

        public Builder type(TransactionType t) {
            this.type = t;
            return this;
        }

        public Builder amount(int a) {
            this.amount = a;
            return this;
        }

        public Builder details(String d) {
            this.details = d;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    @Override
    public Transaction clone() {
        try {
            return (Transaction) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return timestamp + " | " + type + " | " + amount + " | " + details;
    }
}