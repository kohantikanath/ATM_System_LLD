package app.components.impl;

import app.components.*;

public class SimpleDisplay implements Display {
    public void show(String message) {
        System.out.println("[DISPLAY] " + message);
    }
}