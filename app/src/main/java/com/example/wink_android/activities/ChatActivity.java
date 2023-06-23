package com.example.wink_android.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.Message;
import com.example.wink_android.R;
import com.example.wink_android.adapters.Messages_RecycleView_Adapter;

import com.example.wink_android.databinding.ActivityChatBinding;
import com.example.wink_android.general.Constants;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.general.Utilities;
import com.example.wink_android.view.ChatViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private ArrayList<Message> messagesArr;
    private final int DEFAULT_INT = 0;


    int chatId;

    Chat chat;

    Messages_RecycleView_Adapter adapter;
    ChatViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel=new ChatViewModel();
        setTheme();
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        chatId = (intent.getIntExtra("id",DEFAULT_INT));

        chat = viewModel.getChatById(chatId);
        setConnectUser();
        adapter = new Messages_RecycleView_Adapter(this, chat.getOtherUsername());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // back to the contact list
        binding.backBtn.setOnClickListener(view -> {
            this.finish();
        });

        viewModel.updateMessagesByChatId(chat.getId());
        binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);

        viewModel.getMessages().observe(this, messages -> {
            //filter messages by chat id
            int current_len = adapter.getItemCount();
            messages = messages.stream().filter(message -> message.getChatId() == chat.getId()).collect(Collectors.toList());
            adapter.setMessages(messages);
            //if message is added scroll to the last message
            if(current_len != adapter.getItemCount()){
                binding.recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });

        // an observer to the massageInput
        binding.massageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            // when the user adds some text, change the send btn state
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.buttonSend.setBackgroundResource(R.drawable.able_send_btn);
                } else {
                    binding.buttonSend.setBackgroundResource(R.drawable.unable_send_btn);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // insert new msg to the messages array
        binding.buttonSend.setOnClickListener(view -> {
            //get the contact
            String messageContent = binding.massageInput.getText().toString();
            //if there is some text
            if(!messageContent.isEmpty()){
                binding.massageInput.setText("");
                binding.buttonSend.setBackgroundResource(R.drawable.unable_send_btn);
                viewModel.sendMessage(chat.getId(), messageContent);
                viewModel.updateMessagesByChatId(chat.getId());
            }
        });

        viewModel.getStatus().observe(this, v->{
           if (Objects.equals(v, Constants.FAILED_CONNECT_TO_SERVER)){
                showAlert("connection to server failed....");
                viewModel.setInitialStatus();
            }

        });
    }
    private void setConnectUser(){
        binding.contactName.setText(chat.getOtherUsername());
        binding.profilePic.setImageDrawable(new OvalImageDrawable(Utilities.stringToBitmap(chat.getOtherProfilePic())));
    }
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
    private void showAlert(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_incorrect_url, null);
        EditText editText = dialogView.findViewById(R.id.popup_incorrect_tv); // Replace with your actual EditText ID
        editText.setText(errorMessage); // Set the error message text here

        builder.setView(dialogView)
                .setTitle("Error!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    // Perform any necessary action on positive button click
                    dialogInterface.dismiss();
                })
                .setCancelable(true)
                .show();

    }

//    private String getCurrentTime() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return sdf.format(calendar.getTime());
//    }
}
