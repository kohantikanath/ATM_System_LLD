// Filename: Main.java
// Entry point to run the ATM simulation

public class Main {
    public static void main(String[] args) {
        // Build an ATM using factory
        var atm = app.atm.ATM.getInstance();
        atm.start();

        // Simulate card insertion
        atm.insertCard(new app.card.Card("BANK-123", "1234-5678-9012-3456"));

        // Simulate entering pin
        atm.enterPin("1234");

        // Choose withdrawal
        atm.requestWithdrawal(1860); // tests minimal currency algorithm

        // Print mini statement
        atm.printMiniStatement();

        // Change pin
        atm.changePin("4321");

        // Make deposit
        atm.depositCash(2000);

        // Eject card
        atm.ejectCard();

        atm.shutdown();
    }
}