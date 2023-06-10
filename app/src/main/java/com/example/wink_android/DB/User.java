package com.example.wink_android.DB;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String displayName;
    private String profilePic;
    private List<Integer> chats;
    @Ignore
    public User(int id,String username,String displayName,String profilePic,List<Integer> chats) {
        this.id = id;
        this.username =username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.chats = chats;
    }

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public List<Integer> getChats() {
        return chats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setChats(List<Integer> chats) {
        this.chats = chats;
    }
}
