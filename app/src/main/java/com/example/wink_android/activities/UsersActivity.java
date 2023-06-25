package com.example.wink_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
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
import com.example.wink_android.general.Constants;
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
    boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
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
        });
        viewModel.updateChats(fireBaseToken);


//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
//            String newToken= instanceIdResult.getToken();
//            fireBaseToken=newToken;
//        });
//        viewModel.updateChats(fireBaseToken);
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
            Intent i = new Intent(UsersActivity.this, Login.class);
            startActivity(i);
            finish();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.updateChats(fireBaseToken);
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getStatus().observe(this,v->{
            //the user details arrived successfully
            if(Objects.equals(v, Constants.SUCCESSFUL_LOGIN)){
                setConnectUser();
            //the user tried to add a chat the doesn't exist
            }else if(Objects.equals(v, "failed add chat - incorrect user")){
                showAlert("failed add chat - incorrect user");
            //disconnect the user
            } else if (Objects.equals(v, Constants.FAILED_CONNECT_TO_SERVER)) {
                showAlert(Constants.FAILED_CONNECT_TO_SERVER);
//                Toast.makeText(getApplicationContext(), v, Toast.LENGTH_SHORT).show();
//                //todo - disconnect if the user is incorrect
            }
        });



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setConnectUser();
    }

    private void setConnectUser(){
        user = viewModel.getConnectUser();
        binding.userPhoto.setImageDrawable(new OvalImageDrawable(Utilities.stringToBitmap(user.getProfilePic())));
        //binding.userPhoto.setImageBitmap(Utilities.stringToBitmap(user.getProfilePic()));
        binding.userName.setText(user.getDisplayName());

    }
    private void showAlert(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup, null);
        EditText editText = dialogView.findViewById(R.id.text); // Replace with your actual EditText ID
        editText.setText(errorMessage); // Set the error message text here

        builder.setView(dialogView)
                .setTitle("Error!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    // Perform any necessary action on positive button click
                    dialogInterface.dismiss();
                })
                .setCancelable(true)
                .show();
        viewModel.setInitialStatus();
    }
}


