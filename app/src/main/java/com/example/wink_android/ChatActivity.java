package com.example.wink_android;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wink_android.databinding.ActivityChatBinding;


public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView textView = new TextView(this);
        textView.setText("hello");
        textView.setBackgroundResource(R.drawable.sent_message);

        //FrameLayout parentLayout = findViewById(R.id.test);
        //parentLayout.addView(textView);



        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
        });

        binding.massageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.buttonSend.setBackgroundResource(R.color.able_send_btn);
                } else {
                    binding.buttonSend.setBackgroundResource(R.color.unable_send_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
