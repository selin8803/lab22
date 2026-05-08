package com.example.lab22;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private ArrayList<User> users = UsersApp.loadUsers();

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        for (User user : users) {

            if (user.getName().equals(username)
                    && user.getPassword().equals(password)) {

                openWelcomeScreen();
                return;
            }
        }

        messageLabel.setText("user or password do not match");
    }

    private void openWelcomeScreen() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/com.example.lab22/Welcome.fxml")
            );

            Stage stage = (Stage) usernameField.getScene().getWindow();

            Scene scene = new Scene(root, 600, 400);

            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}