// Cash dispenser implementing Strategy for minimal notes
package app.components.impl;

import app.components.CashDispenser;
import java.util.*;

public class GreedyCashDispenser implements CashDispenser {
    private final List<Integer> denominations = Arrays.asList(2000, 500, 200, 100, 50, 20, 10);
    private final Map<Integer, Integer> stock = new HashMap<>();

    public GreedyCashDispenser() {
        for (int d : denominations)
            stock.put(d, 50); // initial stock
    }

    @Override
    public Map<Integer, Integer> dispense(int amount) {
        Map<Integer, Integer> used = new LinkedHashMap<>();
        int left = amount;

        for (int d : denominations) {
            int need = left / d;
            int avail = stock.getOrDefault(d, 0);
            int take = Math.min(need, avail);
            if (take > 0) {
                used.put(d, take);
                left -= take * d;
            }
        }

        if (left != 0)
            return null;

        // commit
        used.forEach((k, v) -> stock.put(k, stock.get(k) - v));
        return used;
    }
}