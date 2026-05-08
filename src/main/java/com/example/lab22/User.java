package com.example.lab22;

public class User {
    private String username;
    private String password;

    public User(String username,String password) throws Exception{
        if (username.length() > 50) {
            throw new Exception("Username is too long, try something shorter");
        }

        if (!isValidEmail(username)) {
            throw new Exception("Please enter a valid Email as username");
        }

        if (password.length() < 8) {
            throw new Exception("Your password is too short, add more characters");
        }

        if (password.length() > 12) {
            throw new Exception("Your password is too long, try a shorter one");
        }

        if (!isValidPassword(password)) {
            throw new Exception("Please enter a valid password");
        }

        this.username = username;
        this.password = password;
    }

    private boolean isValidEmail(String username){
        if (username.length() > 50) return false;

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9][A-Za-z0-9.-]*\\.[A-Za-z]{2,}$";
        return username.matches(regex);
    }

    private boolean isValidPassword(String password){
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSymbol = true;
        }

        return hasLetter && hasDigit && hasSymbol;

    }
    public String toString() {
        return username;
    }
    public String getName() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}//endclass
