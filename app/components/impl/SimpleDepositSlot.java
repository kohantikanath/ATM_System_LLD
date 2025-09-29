// DepositSlot implementation
package app.components.impl;

import app.components.DepositSlot;

public class SimpleDepositSlot implements DepositSlot {
    @Override
    public boolean hasEnvelope() {
        return true; /* simulate envelope inserted */
    }
}