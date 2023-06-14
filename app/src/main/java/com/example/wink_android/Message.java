package com.example.wink_android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Message {
    private int id;
    private String time;
    //the id of the user that created the msg
    private int created;
    private String content;

    public Message() {
    }

    public Message(int id, String time, int created, String content) {
        this.id = id;
        this.time = time;
        this.created = created;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        time = sdf.format(calendar.getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}

