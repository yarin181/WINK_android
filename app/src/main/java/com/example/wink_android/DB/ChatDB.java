package com.example.wink_android.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wink_android.DB.daoes.SettingsInfoDao;
import com.example.wink_android.DB.entities.SettingsInfo;

@Database(entities = {User.class, Chat.class, Message.class, SettingsInfo.class}, version = 41)
public abstract class ChatDB extends RoomDatabase {
    private static ChatDB instance;

    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();

    public abstract SettingsInfoDao settingsInfoDao();

    public static synchronized ChatDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ChatDB.class, "chatDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
