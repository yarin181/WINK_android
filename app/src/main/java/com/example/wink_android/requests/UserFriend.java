package com.example.wink_android.requests;

public class UserFriend implements Request{
    private int id;
    private BasicUserData user;
    private LastMessage lastMessage;
    public UserFriend(int id, BasicUserData user, LastMessage lastMessage ){
        this.id=id;
        this.user=user;
        this.lastMessage=lastMessage;
    }
    public UserFriend(int id, BasicUserData user){
        this.id=id;
        this.user=user;
    }

    public int getId() {
        return id;
    }

    public BasicUserData getUser() {
        return user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }
}
