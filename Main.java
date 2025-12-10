package atmsystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Initially 3 pre-registered users
        ATM[] users = new ATM[10]; // can hold up to 10 users
        int userCount = 3;

        users[0] = new ATM("Ahmed", 1111, 1234, 5000.0);
        users[1] = new ATM("Ali", 2222, 2345, 8000.0);
        users[2] = new ATM("Hassan", 3333, 3456, 9000.0);

        System.out.println("=================================");
        System.out.println("      JAVA ATM SYSTEM PROJECT    ");
        System.out.println("=================================");

        int mainChoice;
        do {
            System.out.println("\n1. Login");
            System.out.println("2. Create New Account");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = input.nextInt();

            switch (mainChoice) {
                case 1: {
                    int attempts = 0, index = -1;
                    while (attempts < 3 && index == -1) {
                        System.out.print("\nEnter Account Number: ");
                        int acc = input.nextInt();

                        System.out.print("Enter PIN: ");
                        int pin = input.nextInt();

                        for (int i = 0; i < userCount; i++) {
                            if (users[i].accountNumber == acc && users[i].login(pin)) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1) {
                            System.out.println("Invalid account or PIN!");
                            attempts++;
                        }
                    }

                    if (index == -1) {
                        System.out.println("Too many failed attempts. Returning to main menu...");
                        break;
                    }

                    System.out.println("\nWelcome, " + users[index].name + "!");
                    int choice;
                    do {
                        System.out.println("\n===== ATM MENU =====");
                        System.out.println("1. Check Balance");
                        System.out.println("2. Deposit Money");
                        System.out.println("3. Withdraw Money");
                        System.out.println("4. Change PIN");
                        System.out.println("5. Display Account Info");
                        System.out.println("0. Logout");
                        System.out.print("Enter choice: ");
                        choice = input.nextInt();

                        switch (choice) {
                            case 1 -> users[index].checkBalance();
                            case 2 -> {
                                System.out.print("Enter amount to deposit: ");
                                double dep = input.nextDouble();
                                users[index].deposit(dep);
                            }
                            case 3 -> {
                                System.out.print("Enter amount to withdraw: ");
                                double wd = input.nextDouble();
                                users[index].withdraw(wd);
                            }
                            case 4 -> {
                                System.out.print("Enter old PIN: ");
                                int oldPin = input.nextInt();
                                System.out.print("Enter new PIN: ");
                                int newPin = input.nextInt();
                                users[index].changePin(oldPin, newPin);
                            }
                            case 5 -> users[index].displayInfo();
                            case 0 -> System.out.println("Logging out...");
                            default -> System.out.println("Invalid choice!");
                        }
                    } while (choice != 0);
                    break;
                }

                case 2: {
                    if (userCount >= users.length) {
                        System.out.println("Maximum user limit reached!");
                        break;
                    }

                    System.out.print("\nEnter your name: ");
                    input.nextLine();
                    String name = input.nextLine();

                    System.out.print("Set your Account Number: ");
                    int accNo = input.nextInt();

                    System.out.print("Set your PIN: ");
                    int pin = input.nextInt();

                    System.out.print("Enter initial deposit: ");
                    double balance = input.nextDouble();

                    users[userCount] = new ATM(name, accNo, pin, balance);
                    userCount++;
                    System.out.println("Account created successfully!");
                    break;
                }

                case 0:
                    System.out.println("Thank you for using our ATM!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (mainChoice != 0);
    }
}