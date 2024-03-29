package com.example.wink_android.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... user);

    @Update
    void updateUser(User... user);

    @Delete
    void deleteUser(User... user);

    @Query("SELECT * FROM users LIMIT 1")
    User getUser();

    @Query("DELETE FROM users")
    void deleteAllUsers();

//    @Query("SELECT * FROM users")
//    List<User> getAllUsers();
//    @Query("SELECT * FROM chats WHERE id = :chatId")
//    Chat getChatById(int chatId);
//
//    @Insert
//    long insertChat(Chat chat);
//
//    @Update
//    void updateChat(Chat chat);
//
//    @Delete
//    void deleteChat(Chat chat);


}
