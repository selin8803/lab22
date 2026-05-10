package com.example.lab22;

public class User {
    private String username;
    private String password;
    private int failedAttempts; //the number of wrong attempts for this user
    private long blockedTime; //the time when the user was blocked
    private boolean blocked; //true if the user is currently blocked

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
        this.failedAttempts = 0;
        this.blockedTime = 0;
        this.blocked = false;
    }
//check if the email is valid
    private boolean isValidEmail(String username){
        if (username.length() > 50) return false;

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9][A-Za-z0-9.-]*\\.[A-Za-z]{2,}$";
        return username.matches(regex);
    }
//check if the password is valid
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
    public synchronized int getFailedAttempts() { // synchronized because more than one thread can access this value
        return failedAttempts;}

    public synchronized boolean isBlocked() { // synchronized because more than one thread can check/change blocked
        return blocked;}

    public synchronized long getBlockedTime() { // synchronized because more than one thread can access the block time
        return blockedTime;}

    public synchronized void addFailedAttempt() { //this function adds one failed attempt
        failedAttempts++;}

    // This function blocks the user
    public synchronized void blockUser() {
        blocked = true;
        blockedTime = System.currentTimeMillis();
        failedAttempts = 0;
    }

    // This method unblocks the user after t seconds
    public synchronized void unblockUser() {
        blocked = false;
        blockedTime = 0;
        failedAttempts = 0;
    }

    public synchronized void resetFailedAttempts() { //this function resets the attempts after a successful login
        failedAttempts = 0;}
}//endclass
