package com.example.wink_android.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.ChatDao;
import com.example.wink_android.DB.UserDao;

import java.util.List;

public class ChatRepository {
    private ChatDB chatDB;
    private UserDao userDao;

    private ChatDao chatDao;



    public ChatRepository(){
//        chatDB = Room.databaseBuilder(getApplicationContext(),ChatDB.class,"ChatDB")
//                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        chatDB = ChatDB.getInstance(null);
        userDao = chatDB.userDao();
        chatDao = chatDB.chatDao();

    }

    public LiveData<List<Chat>> getChats(){
        return chatDao.getAllChats();
    }

    public void add(Chat chat){
        chatDao.insertChat(chat);
    }

    public void delete(Chat chat){
        chatDao.deleteChat(chat);
    }
    public void reload(){
        ///reload
    }



}
