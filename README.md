# ATM System 🏧

A comprehensive ATM (Automated Teller Machine) simulation system built in Java, demonstrating various design patterns and object-oriented programming principles.

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Components](#components)
- [Transaction Flow](#transaction-flow)
- [Extensibility](#extensibility)
- [Contributing](#contributing)

## ✨ Features

- **Card Management**: Insert and eject cards with secure PAN masking
- **PIN Authentication**: Secure PIN verification through bank services
- **Cash Withdrawal**: Smart cash dispensing with optimal denomination algorithm
- **Cash Deposit**: Deposit functionality with envelope detection
- **Mini Statements**: View transaction history from bank records
- **PIN Change**: Secure PIN modification capability
- **Transaction Logging**: Automatic transaction recording and receipt printing
- **Bank Integration**: Extensible architecture supporting multiple banks

## 🏗️ Architecture

The ATM system follows a layered architecture with clear separation of concerns:

```
┌─────────────────┐
│   Presentation  │  Main.java
├─────────────────┤
│    Facade       │  ATM.java (Singleton)
├─────────────────┤
│  State Machine  │  ATMState implementations
├─────────────────┤
│   Components    │  Hardware abstractions
├─────────────────┤
│  Bank Services  │  External bank integration
├─────────────────┤
│   Data Models   │  Card, Transaction
└─────────────────┘
```

## 🎨 Design Patterns

The system implements multiple design patterns for maintainable and extensible code:

| Pattern       | Implementation            | Purpose                         |
| ------------- | ------------------------- | ------------------------------- |
| **Singleton** | `ATM`, `ComponentFactory` | Single instance management      |
| **State**     | `ATMState` interface      | Handle different ATM states     |
| **Facade**    | `ATM` class               | Unified interface to subsystems |
| **Factory**   | `ComponentFactory`        | Component creation abstraction  |
| **Builder**   | `Transaction.Builder`     | Complex object construction     |
| **Observer**  | `TransactionLog`          | Event notification system       |
| **Strategy**  | `GreedyCashDispenser`     | Algorithm encapsulation         |
| **Adapter**   | `BankConnector`           | External service integration    |

## 📁 Project Structure

```
ATM_System/
│
├── Main.java                     # Entry point
│
└── app/
    ├── atm/
    │   └── ATM.java              # Main ATM controller (Singleton + Facade)
    │
    ├── state/                    # State Pattern Implementation
    │   ├── ATMState.java         # State interface
    │   ├── IdleState.java        # Waiting for card
    │   ├── CardInsertedState.java # Waiting for PIN
    │   └── AuthenticatedState.java # Authenticated operations
    │
    ├── components/               # Hardware Abstractions
    │   ├── CardReader.java
    │   ├── PinEntry.java
    │   ├── CashDispenser.java
    │   ├── Printer.java
    │   ├── DepositSlot.java
    │   ├── Display.java
    │   │
    │   └── impl/                 # Component Implementations
    │       ├── SimpleCardReader.java
    │       ├── SimplePinEntry.java
    │       ├── GreedyCashDispenser.java
    │       ├── SimplePrinter.java
    │       ├── SimpleDepositSlot.java
    │       └── SimpleDisplay.java
    │
    ├── factory/
    │   └── ComponentFactory.java # Factory for components
    │
    ├── bank/                     # Bank Service Layer
    │   ├── BankService.java      # Bank interface
    │   ├── BankConnector.java    # Adapter for bank services
    │   └── MockBankService.java  # Mock implementation
    │
    ├── card/
    │   └── Card.java             # Card data model
    │
    ├── transaction/              # Transaction System
    │   ├── Transaction.java      # Transaction model (Builder pattern)
    │   └── TransactionType.java  # Transaction types enum
    │
    └── tx/                       # Transaction Logging (Observer)
        ├── TransactionListener.java
        └── TransactionLog.java
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Any Java IDE (optional)

### Installation

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd ATM_System
   ```

2. **Compile the project**:

   ```bash
   javac Main.java
   ```

3. **Run the application**:
   ```bash
   java Main
   ```

## 💡 Usage

The system comes with a pre-configured demo in `Main.java` that demonstrates all features:

```java
public class Main {
    public static void main(String[] args) {
        var atm = app.atm.ATM.getInstance();
        atm.start();

        // Insert card
        atm.insertCard(new app.card.Card("BANK-123", "1234-5678-9012-3456"));

        // Enter PIN
        atm.enterPin("1234");

        // Perform operations
        atm.requestWithdrawal(1860);
        atm.printMiniStatement();
        atm.changePin("4321");
        atm.depositCash(2000);

        atm.ejectCard();
        atm.shutdown();
    }
}
```

### Expected Output

```
[DISPLAY] ATM is starting...
[DISPLAY] Idle: Please insert your card
[DISPLAY] Card inserted: ****-****-****-3456
[DISPLAY] Card read. Please enter PIN
[DISPLAY] Authenticated. Choose transaction
--- RECEIPT ---
2025-09-29T05:41:24.692977900Z | WITHDRAWAL | 1860 | Dispensed: {500=3, 200=1, 100=1, 50=1, 10=1}
--- END ---
--- MINI STATEMENT ---
2025-09-29T05:41:24.692977900Z | WITHDRAWAL | 1860 |
--- END MINI ---
[DISPLAY] PIN changed successfully
--- RECEIPT ---
2025-09-29T05:41:24.723140900Z | DEPOSIT | 2000 | Deposit received
--- END ---
[DISPLAY] Ejecting card...
[DISPLAY] ATM shutting down...
```

## 🔧 Components

### Core Components

- **ATM Controller**: Main orchestrator implementing Facade and Singleton patterns
- **State Machine**: Handles different ATM operational states
- **Component Factory**: Creates hardware component instances
- **Bank Connector**: Adapts external bank services to ATM interface

### Hardware Components

- **Card Reader**: Handles card insertion/ejection
- **PIN Entry**: Manages PIN input
- **Cash Dispenser**: Implements greedy algorithm for optimal note dispensing
- **Printer**: Handles receipt and statement printing
- **Deposit Slot**: Manages cash deposit operations
- **Display**: Shows messages to users

## 🔄 Transaction Flow

1. **Card Insertion** → `IdleState` → `CardInsertedState`
2. **PIN Entry** → Verification → `AuthenticatedState`
3. **Transaction Operations**:
   - Withdrawal: Balance check → Cash dispensing → Receipt
   - Deposit: Envelope check → Credit account → Receipt
   - Statement: Fetch from bank → Print
   - PIN Change: Validate → Update → Confirm
4. **Card Ejection** → Return to `IdleState`

## 🔧 Extensibility

The system is designed for easy extension:

### Adding New Hardware

```java
// 1. Create interface in app.components
public interface NewComponent {
    void performAction();
}

// 2. Implement in app.components.impl
public class NewComponentImpl implements NewComponent {
    @Override
    public void performAction() {
        // Implementation
    }
}

// 3. Add to ComponentFactory
public NewComponent createNewComponent() {
    return new NewComponentImpl();
}
```

### Adding New Bank

```java
// Implement BankService interface
public class NewBankService implements BankService {
    // Implement all required methods
}

// Use in ATM constructor
this.bankConnector = new BankConnector(new NewBankService());
```

### Adding New Transaction Types

```java
// 1. Add to TransactionType enum
public enum TransactionType {
    WITHDRAWAL, DEPOSIT, PIN_CHANGE, BALANCE_INQUIRY // New type
}

// 2. Add method to ATMState
default void balanceInquiry() {
    throw new UnsupportedOperationException();
}

// 3. Implement in AuthenticatedState
public void balanceInquiry() {
    atm.performBalanceInquiry();
}
```


