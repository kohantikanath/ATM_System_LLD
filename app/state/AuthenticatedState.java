// AuthenticatedState
package app.state;

import app.atm.ATM;

public class AuthenticatedState implements ATMState {
    private ATM atm;

    public AuthenticatedState(ATM atm) {
        this.atm = atm;
    }

    public void onEnter() {
        atm.getDisplay().show("Authenticated. Choose transaction");
    }

    public void withdraw(int amount) {
        atm.performWithdrawal(amount);
    }

    public void deposit(int amount) {
        atm.performDeposit(amount);
    }

    public void printMiniStatement() {
        atm.performPrintMiniStatement();
    }

    public void changePin(String newPin) {
        atm.performChangePin(newPin);
    }
}