package com.example.lab22;

import java.io.*;
import java.util.*;

public class UsersApp {

    public static ArrayList<User> loadUsers() {

        ArrayList<User> users = new ArrayList<>();

        try {

            File file = new File("Users.txt");
            Scanner scanner = new Scanner(file);

            PrintWriter writer = new PrintWriter("myOutput.txt");

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");

                if (parts.length != 2) continue;

                try {

                    User user = new User(parts[0], parts[1]);
                    users.add(user);

                } catch (Exception e) {

                    System.out.println(line + " -> " + e.getMessage());
                }
            }

            scanner.close();

            Collections.sort(users, (u1, u2) ->
                    u1.getName().compareTo(u2.getName())
            );

            for (User u : users) {

                writer.print(u.getName() + " " + u.getPassword());
                writer.print("\n");
            }

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return users;
    }
}