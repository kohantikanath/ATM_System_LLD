// =========================
// Filename: app/factory/ComponentFactory.java
// Singleton factory
// =========================
package app.factory;

import app.components.*;
import app.components.impl.*;

public class ComponentFactory {
    private static ComponentFactory instance;

    private ComponentFactory() {
    }

    public static synchronized ComponentFactory getInstance() {
        if (instance == null)
            instance = new ComponentFactory();
        return instance;
    }

    public CardReader createCardReader() {
        return new SimpleCardReader();
    }

    public PinEntry createPinEntry() {
        return new SimplePinEntry();
    }

    public CashDispenser createCashDispenser() {
        return new GreedyCashDispenser();
    }

    public Printer createPrinter() {
        return new SimplePrinter();
    }

    public DepositSlot createDepositSlot() {
        return new SimpleDepositSlot();
    }

    public Display createDisplay() {
        return new SimpleDisplay();
    }
}