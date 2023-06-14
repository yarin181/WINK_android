package com.example.wink_android.requests;

public class MessageAnswer {
    private int id;
    private String created;
    private BasicUserData sender;
    private String content;
    public MessageAnswer( int id, String created, BasicUserData sender, String content){
        this.id=id;
        this.created=created;
        this.sender=sender;
        this.content=content;
    }

    public int getId() {
        return id;
    }
}
