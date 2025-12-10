package atmsystem;

// Inherits from Account
public class ATM extends Account {
    private int pin;

    // Constructor
    public ATM(String name, int accountNumber, int pin, double balance) {
        super(name, accountNumber, balance);
        this.pin = pin;
    }

    // Method overloading example
    public boolean login(int enteredPin) {
        return this.pin == enteredPin;
    }

    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited Rs. " + amount);
        } else {
            System.out.println("Invalid amount!");
        }
    }

    // Withdraw
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrawn Rs. " + amount);
        } else {
            System.out.println("Invalid or insufficient balance!");
        }
    }

    // Change PIN
    public void changePin(int oldPin, int newPin) {
        if (this.pin == oldPin) {
            this.pin = newPin;
            System.out.println("PIN changed successfully!");
        } else {
            System.out.println("Incorrect old PIN!");
        }
    }
}