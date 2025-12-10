package atmsystem;

// Base class for inheritance
public class Account {
    protected String name;
    protected int accountNumber;
    protected double balance;

    public Account(String name, int accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void checkBalance() {
        System.out.println("Current Balance: Rs. " + balance);
    }

    public void displayInfo() {
        System.out.println("\n--- Account Info ---");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: Rs. " + balance);
    }
}