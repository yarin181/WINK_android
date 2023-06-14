package com.example.wink_android.requests;

public class LastMessage {
    private int id;
    private String created;
    private String content;
    public LastMessage(int id, String created,String content){
        this.id=id;
        this.created=created;
        this.content=content;
    }

    public String getContent() {
        return content;
    }
}
