package com.example.wink_android.requests;

public class MessageAnswer implements Request{
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

    public String getCreated() {
        return created;
    }

    public BasicUserData getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}
