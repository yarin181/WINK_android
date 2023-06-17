package com.example.wink_android.requests;

public class ServerAnswer {
    private Request data;
    private int status;
    public ServerAnswer(){}

    public void setData(Request data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Request getData() {
        return data;
    }
}
