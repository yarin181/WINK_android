package com.example.wink_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Dao;
import androidx.room.Room;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.databinding.ActivityUsersBinding;
import com.example.wink_android.view.ChatViewModel;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ActivityUsersBinding binding;

    private ListView lvUsers;
    //private List<String> users;
    private List<User> dbUsers;
    private List<Chat> chats;
    private ArrayAdapter<Chat> chatsAdapter;

    private ChatDB chatDB;

    private ChatViewModel viewModel;

    ActivityResultLauncher<Intent> activityLauncher;

    LiveData<Chat> chat;

    User user;
    String connectUsernameString;
    boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectUsernameString= "yarin";

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatDB = ChatDB.getInstance(getApplicationContext());

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setConnectUser("yarin!!");
////        viewModel.getChats().observe(this, chat -> {
////
////        });


        //renderUsers();

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String username = intent.getStringExtra("username");
                            viewModel.addContactByUsername(username);
                            Log.i("Viwe","chats: " + viewModel.getChats().toString());
                            //chat= viewModel.getChatByUsername(username);

                            //userDao.insertUser(new User(username));
                            //viewModel.getChats().setValue(new Chat(username,username,connectPhotoString));
                        }
                    }
                });

        viewModel.getChats().observe(this, v->{
            if (v.size() != 0){
                Toast.makeText(getApplicationContext(), v.get(v.size()-1).getOtherDisplayName(), Toast.LENGTH_LONG).show();
            }
        });

        binding.addContact.setOnClickListener(view -> {
            Intent intent = new Intent(UsersActivity.this, AddUserActivity.class);
            activityLauncher.launch(intent);
        });

        binding.settingsButton.setOnClickListener(v-> {
            Log.i("UsersActivity" ,"settings");
            viewModel.editSettings();
        });
        binding.logoutButton.setOnClickListener(v ->{
            Log.i("logout","the IP is : "+ viewModel.getIp());
        });

        setConnectUser();
    }


    private void setConnectUser(){
        user = viewModel.getConnectUser();
        byte[] decodedImage = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        binding.userPhoto.setImageBitmap(bitmap);
        binding.userName.setText(user.getUsername());
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        renderUsers();
//    }
//    private void renderUsers() {
//        lvUsers = binding.contactsList;
//
//
//        lvUsers.setAdapter(chatsAdapter);
//    }
//



//    private void renderUsers() {
//        lvUsers = binding.contactsList;
//        //users = new ArrayList<>();
//        chatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chats){
//
//        }
//        chatsAdapter.notifyDataSetChanged();
//        //loadPosts();
//
//        lvUsers.setAdapter(chatsAdapter);
////        lvUsers.setOnItemClickListener((adapterView, view, i, l) -> {
////            Intent intent = new Intent(this, A.class);
////            intent.putExtra("id", dbPosts.get(i).getId());
////            startActivity(intent);
////        });
//
////        lvUsers.setOnItemLongClickListener((adapterView, view, i, l) -> {
////            posts.remove(i);
////            Post post = dbPosts.remove(i);
////            postDao.delete(post);
////            usersAdapter.notifyDataSetChanged();
////            return true;
////        });
//    }

//    private void loadUsers() {
//        //users.clear();
//        //dbUsers = userDao.getAllUsers();
//
////        for (Chat chat : chats){
////            Log.d("UsersActivity" ,chat.getOtherUsername());
////            chats.add(chat.getUsername());
////        }
////        usersAdapter.notifyDataSetChanged();
//    }
}
