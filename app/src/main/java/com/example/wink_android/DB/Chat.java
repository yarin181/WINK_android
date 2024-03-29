package com.example.wink_android.DB;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
//import java.util.List;

@Entity(tableName = "chats")
public class Chat {
    @PrimaryKey
    private final int id;
    private String otherUsername;
    private String otherDisplayName;
    private String otherProfilePic;
    private String lastMessageContent;

    private String lastMessageCreated;

//    @Ignore
//    Message lsatMessage;

    public Chat(int id,String lastMessageContent,String lastMessageCreated, String otherUsername, String otherDisplayName, String otherProfilePic) {
        this.id = id;
        this.lastMessageContent = lastMessageContent;
        this.lastMessageCreated = lastMessageCreated;
        this.otherUsername = otherUsername;
        this.otherDisplayName = otherDisplayName;
        this.otherProfilePic = otherProfilePic;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public String getLastMessageCreated() {
        return lastMessageCreated;
    }

    public void setLastMessageCreated(String lastMessageCreated) {
        this.lastMessageCreated = lastMessageCreated;
    }
//    public int getLastMessageId() {
//        return lastMessageId;
//    }
//
//    public void setLastMessageId(int lastMessageId) {
//        this.lastMessageId = lastMessageId;
//    }

//    public Message getLsatMessage() {
//        return lsatMessage;
//    }
//
//    public void setLsatMessage(Message lsatMessage) {
//        this.lsatMessage = lsatMessage;
//    }

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

    public void setOtherUsername(@NonNull String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public void setOtherDisplayName(String otherDisplayName) {
        this.otherDisplayName = otherDisplayName;
    }

    public int getId() {
        return id;
    }

    public void setOtherProfilePic(String otherProfilePic) {
        this.otherProfilePic = otherProfilePic;
    }

}

