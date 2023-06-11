package com.example.wink_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Room;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.databinding.ActivityUsersBinding;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private ChatDB chatDB;
    private ListView lvUsers;
    private List<String> users;
    private List<User> dbUsers;
    private List<Chat> chats;
    private ArrayAdapter<String> usersAdapter;
    private UserDao userDao;
    private String connectUsernameString;
    private String connectPhotoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectUsernameString = "yarin";
        connectPhotoString = "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";

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatDB = Room.databaseBuilder(getApplicationContext(),ChatDB.class,"ChatDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = chatDB.userDao();
        renderUsers();

        binding.addContact.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
        });

        binding.settingsButton.setOnClickListener(v-> {
            Log.d("UsersActivity" ,"settings");
        });

        setConnectUser();
    }
    private void setConnectUser(){
        //userDao.getUser(connectUsernameString);
        byte[] decodedImage = Base64.decode(connectPhotoString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        binding.userPhoto.setImageBitmap(bitmap);
        binding.userName.setText(connectUsernameString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderUsers();
    }
    private void renderUsers() {
        lvUsers = binding.contactsList;
        users = new ArrayList<>();
        usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);

        loadPosts();

        lvUsers.setAdapter(usersAdapter);
//        lvUsers.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(this, A.class);
//            intent.putExtra("id", dbPosts.get(i).getId());
//            startActivity(intent);
//        });

//        lvUsers.setOnItemLongClickListener((adapterView, view, i, l) -> {
//            posts.remove(i);
//            Post post = dbPosts.remove(i);
//            postDao.delete(post);
//            usersAdapter.notifyDataSetChanged();
//            return true;
//        });
    }

    private void loadPosts() {
        users.clear();
        Log.d("UsersActivity" ,"loadPosts");

        dbUsers = userDao.getAllUsers();
        users.add("check1");
        for (User user : dbUsers){
            Log.d("UsersActivity" ,user.getUsername());
            users.add(user.getUsername());
        }
        usersAdapter.notifyDataSetChanged();
    }
}
