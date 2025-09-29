package app.components;

import java.util.Map;

public interface CashDispenser {
    // returns map of denomination -> count or null if cannot dispense
    Map<Integer, Integer> dispense(int amount);
}