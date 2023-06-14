package com.example.wink_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.wink_android.databinding.ActivityChatBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private ArrayList<Message> messagesArr =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // back to the contact list
        binding.backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
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
               //Reset the message input to empty
                binding.massageInput.setText("");
                binding.buttonSend.setBackgroundResource(R.drawable.unable_send_btn);



                //get rhe current time
                String currentTime = getCurrentTime();
                //insert the to the msg Array
                Message newMessage = new Message(1, currentTime, 0, messageContent);
                messagesArr.add(newMessage);
                Messages_RecycleView_Adapter adapter = new Messages_RecycleView_Adapter(this,messagesArr);
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }


        });
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
