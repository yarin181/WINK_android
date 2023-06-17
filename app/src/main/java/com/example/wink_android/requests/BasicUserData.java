package com.example.wink_android.requests;

public class BasicUserData implements Request{
    private String username;
    private String displayName;
    private String profilePic;
    public BasicUserData(String username, String displayName,String profilePic){
        this.username=username;
        this.displayName=displayName;
        this.profilePic=profilePic;
    }
    public BasicUserData(String username){
        this.username=username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }
}
