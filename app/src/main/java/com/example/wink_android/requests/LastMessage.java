package com.example.wink_android.requests;

public class LastMessage implements Request {
    private int id;
    private String created;
    private String content;
    public LastMessage(int id, String created,String content){
        this.id=id;
        this.created=created;
        this.content=content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
