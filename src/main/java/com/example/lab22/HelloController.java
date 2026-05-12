package com.example.lab22;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class HelloController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // get only valid users from UsersApp
    private ArrayList<User> users = UsersApp.loadUsers();

    private static int maxAttempts;
    private static int blockTime;

    // get n and t from main
    public static void setLoginSettings(int n, int t) {

        maxAttempts = n;
        blockTime = t;
    }



    // runs when login button is pressed
    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = findUser(username);

        // user does not exist
        if (user == null) {

            messageLabel.setText("User does not exist");
            return;
        }
        // בדיקה אם יש שדות ריקים
        if(username.trim().isEmpty() || password.trim().isEmpty()) {
            messageLabel.setText("Please fill all fields");
            return;
        }
        // wrong password
        if (!user.getPassword().equals(password)) {

            FailedAttemptThread thread =
                    new FailedAttemptThread(user);

            thread.start();
            return;
        }

        // correct username and password
        CheckBlockedThread thread =
                new CheckBlockedThread(user);

        thread.start();
    }

    // search for user in the list
    private User findUser(String username) {

        for (User user : users) {

            if (user.getName().equals(username)) {

                return user;
            }
        }

        return null;
    }

    // first thread
    // updates failed attempts and blocks the user
    private class FailedAttemptThread extends Thread {

        private User user;

        public FailedAttemptThread(User user) {

            this.user = user;
        }

        @Override
        public void run() {

            // check if already blocked
            if (user.isBlocked()) {

                Platform.runLater(() ->
                        messageLabel.setText(
                                "User is blocked. Please wait.")
                );

                return;
            }

            // add failed attempt
            user.addFailedAttempt();

            // reached maximum attempts
            if (user.getFailedAttempts() >= maxAttempts) {

                user.blockUser();

                Platform.runLater(() ->
                        messageLabel.setText(
                                "User blocked for "
                                        + blockTime
                                        + " seconds")
                );

                try {

                    // wait t seconds
                    Thread.sleep(blockTime * 1000L);

                    // unblock user
                    user.unblockUser();

                    Platform.runLater(() ->
                            messageLabel.setText(
                                    "Block time ended. Try again..")
                    );

                } catch (InterruptedException e) {

                    System.out.println("Thread interrupted");
                }

            } else {

                Platform.runLater(() ->
                        messageLabel.setText(
                                "Wrong password. Attempt "
                                        + user.getFailedAttempts()
                                        + " of "
                                        + maxAttempts)
                );
            }
        }
    }
    // open welcome screen
    private void openWelcomeScreen() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(
                            "/com.example.lab22/Welcome.fxml")
            );

            Stage stage =
                    (Stage) usernameField.getScene().getWindow();

            Scene scene = new Scene(root, 320, 240);

            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Error: could not open welcome screen.");
        }
    }
    // second thread
    // checks if the user is blocked
    private class CheckBlockedThread extends Thread {

        private User user;

        public CheckBlockedThread(User user) {

            this.user = user;
        }

        @Override
        public void run() {

            if (user.isBlocked()) {

                Platform.runLater(() ->
                        messageLabel.setText(
                                "User is blocked. Please wait.")
                );

            } else {

                user.resetFailedAttempts();

                Platform.runLater(() ->
                        openWelcomeScreen()
                );
            }
        }
    }


}//endclass

