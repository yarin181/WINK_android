package com.example.wink_android.requests;

public class MessageRequest implements Request{
    private String msg;
    public MessageRequest(String msg){
        this.msg=msg;
    }
}
