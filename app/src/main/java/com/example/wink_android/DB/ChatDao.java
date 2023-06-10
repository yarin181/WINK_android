package com.example.wink_android.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void insertChat(Chat... chat);

    @Update
    void updateChat(Chat... chat);

    @Delete
    void deleteChat(Chat... chat);

    @Query("SELECT * FROM chats")
    LiveData<List<Chat>> getAllChats();

    @Query("SELECT * FROM chats WHERE otherUsername = :username")
    LiveData<Chat> getChatByUsername(String username);

    @Query("DELETE FROM chats")
    void deleteAllChats();
}
