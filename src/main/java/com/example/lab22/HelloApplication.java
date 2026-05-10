package com.example.lab22;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter max attempts (n):");
        int n = scanner.nextInt();

        System.out.println("Enter block time in seconds (t):");
        int t = scanner.nextInt();

        HelloController.setLoginSettings(n, t);
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("/com.example.lab22/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}