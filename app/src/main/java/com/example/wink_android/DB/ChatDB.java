package com.example.wink_android.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Chat.class, Message.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class ChatDB extends RoomDatabase {
//    private static ChatAppDatabase instance;

    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();

//    public static synchronized ChatAppDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                            ChatAppDatabase.class, "chat_app_database")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }
}
