package com.example.wink_android.requests;

public class LoginRequest implements Request{
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public LoginRequest(String username) {
        this.username = username;
    }

    // Getters and setters (or you can use Lombok annotations)
}
