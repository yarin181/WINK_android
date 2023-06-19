package com.example.wink_android.activities.popupsActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wink_android.R;
import com.example.wink_android.databinding.ActivitySettingsBinding;
import com.example.wink_android.databinding.ActivityUsersBinding;
import com.example.wink_android.view.ChatViewModel;

public class SettingsActivity extends AppCompatActivity {
    ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.wink_android.databinding.ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatViewModel = new ChatViewModel();

        binding.switchTheme.setOnClickListener((View v) -> {
            Toast.makeText(this, "Theme Changed", Toast.LENGTH_SHORT).show();
            chatViewModel.switchThemMode();
        });
        binding.btnSetIP.setOnClickListener((View v) -> {
            chatViewModel.setIP(binding.editTextServerIP.getText().toString());
            Toast.makeText(this, "IP Changed to : " + binding.editTextServerIP.getText().toString(), Toast.LENGTH_SHORT).show();
        });
        binding.backBtn.setOnClickListener((View v) -> {
            finish();
        });

    }

}
