// =========================
// Filename: app/atm/ATM.java
// Singleton ATM, acts as Facade and holds state machine
// =========================

package app.atm;

import app.card.Card;
import app.components.*;
import app.state.*;
import app.bank.*;
import app.transaction.*;
import app.tx.*;
import java.util.*;

public class ATM {
    // Singleton
    private static ATM instance;

    // Components
    private final CardReader cardReader;
    private final PinEntry pinEntry;
    private final CashDispenser cashDispenser;
    private final Printer printer;
    private final DepositSlot depositSlot;
    private final Display display;

    // Bank connection (adapter/proxy)
    private final BankConnector bankConnector;

    // State
    private ATMState state;
    private Card currentCard;
    private TransactionLog txLog = new TransactionLog();

    private ATM() {
        var factory = app.factory.ComponentFactory.getInstance();
        this.cardReader = factory.createCardReader();
        this.pinEntry = factory.createPinEntry();
        this.cashDispenser = factory.createCashDispenser();
        this.printer = factory.createPrinter();
        this.depositSlot = factory.createDepositSlot();
        this.display = factory.createDisplay();

        this.bankConnector = new BankConnector(new app.bank.MockBankService());

        this.state = new IdleState(this);

        // observer: printer listens to tx events
        txLog.addListener((app.tx.TransactionListener) printer);
    }

    public static synchronized ATM getInstance() {
        if (instance == null)
            instance = new ATM();
        return instance;
    }

    // Facade start/stop
    public void start() {
        display.show("ATM is starting...");
        state.onEnter();
    }

    public void shutdown() {
        display.show("ATM shutting down...");
    }

    // External actions
    public void insertCard(Card card) {
        this.currentCard = card;
        display.show("Card inserted: " + card.getMaskedPan());
        changeState(new CardInsertedState(this));
    }

    public void enterPin(String pin) {
        state.enterPin(pin);
    }

    public void requestWithdrawal(int amount) {
        state.withdraw(amount);
    }

    public void depositCash(int amount) {
        state.deposit(amount);
    }

    public void printMiniStatement() {
        state.printMiniStatement();
    }

    public void changePin(String newPin) {
        state.changePin(newPin);
    }

    public void ejectCard() {
        display.show("Ejecting card...");
        currentCard = null;
        changeState(new IdleState(this));
    }

    // package-private helpers used by states
    public void verifyPin(String pin) {
        boolean ok = bankConnector.verifyPin(currentCard, pin);
        if (ok) {
            changeState(new AuthenticatedState(this));
        } else {
            display.show("Invalid PIN");
            changeState(new CardInsertedState(this));
        }
    }

    public void performWithdrawal(int amount) {
        // Check balance via bank
        if (!bankConnector.hasSufficientFunds(currentCard, amount)) {
            display.show("Insufficient funds");
            return;
        }
        // dispense cash using strategy in cashDispenser
        Map<Integer, Integer> dispensed = cashDispenser.dispense(amount);
        if (dispensed == null) {
            display.show("Cannot dispense requested amount with available denominations");
            return;
        }
        // notify bank to debit
        bankConnector.debit(currentCard, amount);
        var tx = new Transaction.Builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(amount)
                .details("Dispensed: " + dispensed.toString())
                .build();
        txLog.record(tx);
        printer.printReceipt(tx);
    }

    public void performDeposit(int amount) {
        if (!depositSlot.hasEnvelope()) {
            display.show("Please insert deposit envelope in the deposit slot");
            return;
        }
        bankConnector.credit(currentCard, amount);
        var tx = new Transaction.Builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .details("Deposit received")
                .build();
        txLog.record(tx);
        printer.printReceipt(tx);
    }

    public void performPrintMiniStatement() {
        var mini = bankConnector.getMiniStatement(currentCard);
        printer.printMiniStatement(mini);
    }

    public void performChangePin(String newPin) {
        boolean ok = bankConnector.changePin(currentCard, newPin);
        if (ok)
            display.show("PIN changed successfully");
        else
            display.show("Failed to change PIN");
    }

    void changeState(ATMState newState) {
        this.state = newState;
        state.onEnter();
    }

    // Expose some components for states
    public Card getCurrentCard() {
        return currentCard;
    }

    public Display getDisplay() {
        return display;
    }

    BankConnector getBankConnector() {
        return bankConnector;
    }

    TransactionLog getTxLog() {
        return txLog;
    }
}