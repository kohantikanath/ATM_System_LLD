// =========================
// Filename: app/state/ATMState.java
// State interface and implementations
// =========================

package app.state;

public interface ATMState {
    default void onEnter() {
    }

    default void enterPin(String pin) {
        throw new UnsupportedOperationException();
    }

    default void withdraw(int amount) {
        throw new UnsupportedOperationException();
    }

    default void deposit(int amount) {
        throw new UnsupportedOperationException();
    }

    default void printMiniStatement() {
        throw new UnsupportedOperationException();
    }

    default void changePin(String newPin) {
        throw new UnsupportedOperationException();
    }
}