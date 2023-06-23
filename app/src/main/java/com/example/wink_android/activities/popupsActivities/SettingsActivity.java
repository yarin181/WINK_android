package com.example.wink_android.activities.popupsActivities;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.wink_android.R;
import com.example.wink_android.databinding.ActivitySettingsBinding;
import com.example.wink_android.view.ChatViewModel;

public class SettingsActivity extends AppCompatActivity {
    ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        chatViewModel = new ChatViewModel();
        setTheme();
        super.onCreate(savedInstanceState);


        com.example.wink_android.databinding.ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set the switchTheme to true
        if(chatViewModel.getTheme()){
            binding.switchTheme.setChecked(true);
        }

        binding.switchTheme.setOnClickListener((View v) -> {
            Toast.makeText(this, "Theme Changed", Toast.LENGTH_SHORT).show();
            chatViewModel.switchThemMode();
            setTheme();
        });
        binding.btnSetIP.setOnClickListener((View v) -> {
            String ip = binding.editTextServerIP.getText().toString().trim();
            if(isValidIp(ip)){
                chatViewModel.setIP(binding.editTextServerIP.getText().toString());
                Toast.makeText(this, "IP Changed to : " + binding.editTextServerIP.getText().toString(), Toast.LENGTH_SHORT).show();
                binding.editTextServerIP.setText("");
                binding.editTextServerIP.setHint("The current IP: " + chatViewModel.getIp());
            }
            else {
                showAlert("incorrect ip adress");
            }
        });
        binding.backBtn.setOnClickListener((View v) -> {
            finish();
        });

        binding.editTextServerIP.setHint("The current IP: " + chatViewModel.getIp());

    }


    private void setTheme() {

        boolean isDarkMode = chatViewModel.getTheme();
        if (isDarkMode) {
            setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            setTheme(R.style.AppTheme_Day);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
    private boolean isValidIp(String ip) {
        if (!TextUtils.isEmpty(ip)) {
            return Patterns.IP_ADDRESS.matcher(ip).matches();
        }
        return false;
    }
    private void showAlert(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup, null);
        EditText editText = dialogView.findViewById(R.id.text); // Replace with your actual EditText ID
        editText.setText(errorMessage); // Set the error message text here
        builder.setView(dialogView)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    // Perform any necessary action on positive button click
                    dialogInterface.dismiss();
                })
                .setCancelable(true)
                .show();
        chatViewModel.setInitialStatus();
    }

}
