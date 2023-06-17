package com.example.wink_android.requests;

public class RegisterRequest implements Request{
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
    public RegisterRequest(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
