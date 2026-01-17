package atmsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

    private ATM[] users = new ATM[10];
    private int userCount = 3;
    private ATM currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Pre-registered users
        users[0] = new ATM("Ahmed", 1111, 1234, 5000.0);
        users[1] = new ATM("Ali", 2222, 2345, 8000.0);
        users[2] = new ATM("Hassan", 3333, 3456, 9000.0);

        primaryStage.setTitle("JavaFX ATM System");
        showLoginScreen(primaryStage);
    }

    private void showLoginScreen(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setStyle("-fx-padding: 20; -fx-background-color: #B3E5FC;"); // light blue

        Label titleLabel = new Label("ATM Login");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField accField = new TextField();
        accField.setPromptText("Account Number");

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("PIN");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #0288D1; -fx-text-fill: white;");

        Button createBtn = new Button("Create Account");
        createBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Label message = new Label();

        loginBtn.setOnAction(e -> {
            try {
                int acc = Integer.parseInt(accField.getText());
                int pin = Integer.parseInt(pinField.getText());

                currentUser = null;
                for (int i = 0; i < userCount; i++) {
                    if (users[i].accountNumber == acc && users[i].login(pin)) {
                        currentUser = users[i];
                        break;
                    }
                }

                if (currentUser != null) {
                    message.setText("");
                    showDashboard(stage);
                } else {
                    message.setText("Invalid Account or PIN!");
                }
            } catch (NumberFormatException ex) {
                message.setText("Enter valid numbers!");
            }
        });

        createBtn.setOnAction(e -> showCreateAccount(stage));

        loginLayout.getChildren().addAll(titleLabel, accField, pinField, loginBtn, createBtn, message);
        stage.setScene(new Scene(loginLayout, 300, 250));
        stage.show();
    }

    private void showCreateAccount(Stage stage) {
        VBox createLayout = new VBox(10);
        createLayout.setStyle("-fx-padding: 20; -fx-background-color: #C8E6C9;"); // light green

        Label titleLabel = new Label("Create Account");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField accField = new TextField();
        accField.setPromptText("Account Number");

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("PIN");

        TextField balanceField = new TextField();
        balanceField.setPromptText("Initial Deposit");

        Button createBtn = new Button("Create Account");
        createBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        Label message = new Label();

        createBtn.setOnAction(e -> {
            try {
                if (userCount >= users.length) {
                    message.setText("Max users reached!");
                    return;
                }

                String name = nameField.getText();
                int acc = Integer.parseInt(accField.getText());
                int pin = Integer.parseInt(pinField.getText());
                double balance = Double.parseDouble(balanceField.getText());

                users[userCount++] = new ATM(name, acc, pin, balance);
                message.setText("Account created successfully!");
            } catch (Exception ex) {
                message.setText("Enter valid details!");
            }
        });

        backBtn.setOnAction(e -> showLoginScreen(stage));

        createLayout.getChildren().addAll(titleLabel, nameField, accField, pinField, balanceField, createBtn, backBtn, message);
        stage.setScene(new Scene(createLayout, 300, 300));
    }

    private void showDashboard(Stage stage) {
        VBox dashLayout = new VBox(10);
        dashLayout.setStyle("-fx-padding: 20; -fx-background-color: #FFF9C4;"); // light yellow

        Label welcomeLabel = new Label("Welcome, " + currentUser.name + "!");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Button checkBtn = new Button("Check Balance");
        Button depositBtn = new Button("Deposit Money");
        Button withdrawBtn = new Button("Withdraw Money");
        Button changePinBtn = new Button("Change PIN");
        Button infoBtn = new Button("Display Account Info");
        Button logoutBtn = new Button("Logout");

        Button[] buttons = {checkBtn, depositBtn, withdrawBtn, changePinBtn, infoBtn, logoutBtn};
        for (Button btn : buttons) btn.setStyle("-fx-background-color: #0288D1; -fx-text-fill: white;");

        checkBtn.setOnAction(e -> showAlert("Balance", "Current Balance: Rs. " + currentUser.balance));
        depositBtn.setOnAction(e -> transactionDialog("Deposit"));
        withdrawBtn.setOnAction(e -> transactionDialog("Withdraw"));
        changePinBtn.setOnAction(e -> changePinDialog());
        infoBtn.setOnAction(e -> showAlert("Account Info", "Name: " + currentUser.name +
                "\nAccount Number: " + currentUser.accountNumber +
                "\nBalance: Rs. " + currentUser.balance));
        logoutBtn.setOnAction(e -> showLoginScreen(stage));

        dashLayout.getChildren().addAll(welcomeLabel, checkBtn, depositBtn, withdrawBtn, changePinBtn, infoBtn, logoutBtn);
        stage.setScene(new Scene(dashLayout, 300, 300));
    }

    private void transactionDialog(String type) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(type);
        dialog.setHeaderText(type + " Amount:");
        dialog.setContentText("Enter amount:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                double amt = Double.parseDouble(input);
                if (type.equals("Deposit")) currentUser.deposit(amt);
                else currentUser.withdraw(amt);
                showAlert(type, "Transaction successful!");
            } catch (Exception e) {
                showAlert("Error", "Invalid amount!");
            }
        });
    }

    private void changePinDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change PIN");
        dialog.setHeaderText("Enter old and new PIN:");

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        PasswordField oldPin = new PasswordField();
        oldPin.setPromptText("Old PIN");
        PasswordField newPin = new PasswordField();
        newPin.setPromptText("New PIN");

        grid.add(new Label("Old PIN:"), 0, 0);
        grid.add(oldPin, 1, 0);
        grid.add(new Label("New PIN:"), 0, 1);
        grid.add(newPin, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) return new Pair<>(oldPin.getText(), newPin.getText());
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            try {
                int oldP = Integer.parseInt(result.getKey());
                int newP = Integer.parseInt(result.getValue());
                currentUser.changePin(oldP, newP);
                showAlert("PIN Change", "PIN changed successfully!");
            } catch (Exception e) {
                showAlert("Error", "Invalid PIN!");
            }
        });
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
