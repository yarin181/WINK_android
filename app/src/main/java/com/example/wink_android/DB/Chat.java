package com.example.wink_android.DB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
//import java.util.List;

@Entity(tableName = "chats")
public class Chat {
    @PrimaryKey @NonNull
    private String otherUsername;
    private String otherDisplayName;
    private String otherProfilePic;
    private List<Integer> messages;

    public Chat(@NonNull String otherUsername, String otherDisplayName, String otherProfilePic, List<Integer> messages) {
        this.otherUsername = otherUsername;
        this.otherDisplayName = otherDisplayName;
        this.otherProfilePic = otherProfilePic;
        this.messages = messages;
    }

    @NonNull
    public String getOtherUsername() {
        return otherUsername;
    }

    public String getOtherDisplayName() {
        return otherDisplayName;
    }

    public String getOtherProfilePic() {
        return otherProfilePic;
    }

    public List<Integer> getMessages() {
        return messages;
    }

    public void setOtherUsername(@NonNull String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public void setOtherDisplayName(String otherDisplayName) {
        this.otherDisplayName = otherDisplayName;
    }

    public void setOtherProfilePic(String otherProfilePic) {
        this.otherProfilePic = otherProfilePic;
    }

    public void setMessages(List<Integer> messages) {
        this.messages = messages;
    }
}

