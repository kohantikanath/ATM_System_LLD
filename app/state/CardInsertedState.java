// CardInsertedState
package app.state;

import app.atm.ATM;

public class CardInsertedState implements ATMState {
    private ATM atm;

    public CardInsertedState(ATM atm) {
        this.atm = atm;
    }

    public void onEnter() {
        atm.getDisplay().show("Card read. Please enter PIN");
    }

    public void enterPin(String pin) {
        atm.verifyPin(pin);
    }
}