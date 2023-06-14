package com.example.wink_android.requests;

public class RegisterRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public RegisterRequest(String username,String password,String displayName,String profilePic){
        this.username=username;
        this.password=password;
        this.displayName=displayName;
        this.profilePic=profilePic;
    }


}
