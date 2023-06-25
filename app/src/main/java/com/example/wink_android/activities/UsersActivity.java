package com.example.wink_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.Message;
import com.example.wink_android.DB.User;
import com.example.wink_android.R;
import com.example.wink_android.activities.popupsActivities.AddUserActivity;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.adapters.ChatsListAdapter;
import com.example.wink_android.adapters.RecyclerViewItemClickListener;
import com.example.wink_android.databinding.ActivityUsersBinding;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.general.Utilities;
import com.example.wink_android.view.ChatViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UsersActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private String connectPhotoString= "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";

    private ActivityUsersBinding binding;

    private ListView lvUsers;
    //private List<String> users;
    private List<User> dbUsers;
    private List<Chat> chats;
    private ArrayAdapter<Chat> chatsAdapter;
    private ChatDB chatDB;
    private String fireBaseToken;

    private ChatViewModel viewModel;

    ActivityResultLauncher<Intent> activityLauncher;

    LiveData<Chat> chat;

    User user;
   // String connectUsernameString;
    boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        setTheme();
        super.onCreate(savedInstanceState);

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatDB = ChatDB.getInstance(getApplicationContext());
        Intent thisIntent = getIntent();
        if(thisIntent.getBooleanExtra("connected",false)){
            setConnectUser();
        }else{
            String receivedString = thisIntent.getStringExtra("nameFromLogin");
            viewModel.setConnectUser(receivedString); /// edit to the name got from Login Page/
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken= instanceIdResult.getToken();
            fireBaseToken=newToken;
            viewModel.updateChats(fireBaseToken);
        });


        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String username = intent.getStringExtra("username");
                            viewModel.addContactByUsername(username);
                        }
                    }
                });
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        RecyclerView recyclerView = binding.lstChats;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewItemClickListener.OnItemClickListener itemClickListener = new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(UsersActivity.this, ChatActivity.class);
                intent.putExtra("id", Objects.requireNonNull(viewModel.getChats().getValue()).get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("long click", "onLongClick: ");
            }
        };
        RecyclerViewItemClickListener itemTouchListener = new RecyclerViewItemClickListener(this, recyclerView, itemClickListener);
        recyclerView.addOnItemTouchListener(itemTouchListener);

        viewModel.getChats().observe(this, v -> {
            if (v != null && v.size() != 0) {
                adapter.setChats(v);
                // Set the last message of each chat
//                Thread thread = new Thread(() -> {
//                    for (Chat chat : v) {
//                        if (chat.getLastMessageId() > 0) {
//                            Message message = viewModel.getMessageById(chat.getLastMessageId());
//                            if (message != null) {
//                                chat.setLsatMessage(message);
//                            }else
//                            {
//                                int x=5;
//                            }
//                        }
//                    }
//                    // Post a runnable to update the adapter on the main thread (UI thread)
//                    new Handler(Looper.getMainLooper()).post(() -> {
//
//                    });
//                });
//                thread.start();
            }
        });



        binding.addContact.setOnClickListener(view -> {
            Intent intent = new Intent(UsersActivity.this, AddUserActivity.class);
            activityLauncher.launch(intent);
        });

        binding.settingsButton.setOnClickListener(v-> {
            Intent intent = new Intent(UsersActivity.this, SettingsActivity.class);
            startActivity(intent);
            viewModel.editSettings();
        });

        binding.logoutButton.setOnClickListener(v ->{
            viewModel.deleteUserDetails();
            finish();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.updateChats(fireBaseToken);
            binding.swipeRefreshLayout.setRefreshing(false);
        });

//        binding.swipeRefreshLayout.setOnRefreshListener(=> {
//            // Perform your refresh logic here
//            // This code will be executed when the user pulls down on the screen
//            // You can fetch new data, update the RecyclerView, etc.
//
//            // Once your refresh logic is complete, call setRefreshing(false) to stop the loading indicator
//            binding.swipeRefreshLayout.isRefreshing = false;
//        }


//        setConnectUser();
        viewModel.getStatus().observe(this,v->{
            //the user details arrived successfully
            if(Objects.equals(v, "success user details")){
                setConnectUser();
            //the user tried to add a chat the doesn't exist
            }else if(Objects.equals(v, "failed add chat - incorrect user")){
                //todo - alert that the user doesn't exist
            //disconnect the user
            } else{
                Toast.makeText(getApplicationContext(), v, Toast.LENGTH_SHORT).show();
                //todo - disconnect if the user is incorrect
            }
        });

    }



    private void setConnectUser(){
        user = viewModel.getConnectUser();
        binding.userPhoto.setImageDrawable(new OvalImageDrawable(Utilities.stringToBitmap(user.getProfilePic())));
        //binding.userPhoto.setImageBitmap(Utilities.stringToBitmap(user.getProfilePic()));
        binding.userName.setText(user.getDisplayName());

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTheme();
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
    private void setTheme() {
        boolean isDarkMode = viewModel.getTheme();
        if (isDarkMode) {
            setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            setTheme(R.style.AppTheme_Day);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}


