package com.example.wink_android.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insertMessage(Message... message);

    @Update
    void updateMessage(Message... message);

    @Delete
    void deleteMessage(Message... message);

    @Query("SELECT * FROM messages WHERE chatId = :chatId")
    LiveData<List<Message>> getMessagesByChatId(int chatId);

    @Query("DELETE FROM messages")
    void deleteAllMessages();

    @Query("SELECT * FROM messages")
    LiveData<List<Message>> getAllMessages();

    //get last message for chat
    @Query("SELECT * FROM messages LIMIT 1")
    LiveData<Message> getLastMessageForChat();

    //get messages by id
    @Query("SELECT * FROM messages WHERE id = :id")
    Message getMessageById(int id);
}
