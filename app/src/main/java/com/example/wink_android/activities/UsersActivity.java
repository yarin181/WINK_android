package com.example.wink_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.User;
import com.example.wink_android.activities.popupsActivities.AddUserActivity;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.adapters.ChatsListAdapter;
import com.example.wink_android.adapters.RecyclerViewItemClickListener;
import com.example.wink_android.databinding.ActivityUsersBinding;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.general.Utilities;
import com.example.wink_android.view.ChatViewModel;

import java.util.List;
import java.util.Objects;


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
   // String connectUsernameString;
    boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connectUsernameString= "yarin";

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatDB = ChatDB.getInstance(getApplicationContext());

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setConnectUser("yarin!!121"); /// edit to the name got from Login Page/

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String username = intent.getStringExtra("username");
                            if (!viewModel.addContactByUsername(username)){
                                Toast.makeText(getApplicationContext(), "not a Valid user name !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
//
//        RecyclerView lstChats = findViewById(R.id.lstChats);

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
                Toast.makeText(getApplicationContext(), "Relax why so Long click...", Toast.LENGTH_LONG).show();
            }
        };
        RecyclerViewItemClickListener itemTouchListener = new RecyclerViewItemClickListener(this, recyclerView, itemClickListener);
        recyclerView.addOnItemTouchListener(itemTouchListener);
//        lstChats.setAdapter(adapter);
//        lstChats.setLayoutManager(new LinearLayoutManager(this));

//        List<Chat> tempLst= new ArrayList<>();
//        tempLst.add(new Chat("robo1","Robo"," 2121"));
//        tempLst.add(new Chat("robo2","Robo"," 2121"));
//        tempLst.add(new Chat("robo3","Robo"," 2121"));
//        tempLst.add(new Chat("robo4","Robo"," 2121"));
//        tempLst.add(new Chat("robo5","Robo"," 2121"));
//        tempLst.add(new Chat("robo6","Robo"," 2121"));
//        tempLst.add(new Chat("robo7","Robo"," 2121"));
//        tempLst.add(new Chat("robo1","Robo"," 2121"));
//        tempLst.add(new Chat("robo2","Robo"," 2121"));
//        tempLst.add(new Chat("robo3","Robo"," 2121"));
//        tempLst.add(new Chat("robo4","Robo"," 2121"));
//        tempLst.add(new Chat("robo5","Robo"," 2121"));
//        tempLst.add(new Chat("robo6","Robo"," 2121"));
//        tempLst.add(new Chat("robo7","Robo"," 2121"));
//        adapter.setChats(tempLst);

//        binding.lstChats.setAdapter(adapter);
//        binding.lstChats.setLayoutManager(new LinearLayoutManager(this));


        viewModel.getChats().observe(this, v->{
            if (v != null && v.size() != 0){
                for (int i =0; i < v.size();i++){
                    Toast.makeText(getApplicationContext(), v.get(i).getOtherDisplayName(), Toast.LENGTH_SHORT).show();
                }
                adapter.setChats(v);

            }
        });

        binding.addContact.setOnClickListener(view -> {
            Intent intent = new Intent(UsersActivity.this, AddUserActivity.class);
            activityLauncher.launch(intent);
        });

        binding.settingsButton.setOnClickListener(v-> {
            Intent intent = new Intent(UsersActivity.this, SettingsActivity.class);
            startActivity(intent);
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
        binding.userPhoto.setImageDrawable(new OvalImageDrawable(Utilities.stringToBitmap(user.getProfilePic())));
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
