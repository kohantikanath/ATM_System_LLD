// IdleState
package app.state;

import app.atm.ATM;

public class IdleState implements ATMState {
    private ATM atm;

    public IdleState(ATM atm) {
        this.atm = atm;
    }

    public void onEnter() {
        atm.getDisplay().show("Idle: Please insert your card");
    }
}