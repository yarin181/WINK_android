package com.example.wink_android.activities.popupsActivities;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;
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


        // Initialize the popup layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_incorrect_url, null);
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);


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
            String url = binding.editTextServerIP.getText().toString().trim();
            if(isValidURL(url)){
                chatViewModel.setIP(binding.editTextServerIP.getText().toString());
                Toast.makeText(this, "IP Changed to : " + binding.editTextServerIP.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            else{


                // Schedule the dismissal of the popup after 5 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override

                    public void run() {
                        // Show the popup when the EditText gains focus
                        popupWindow.showAtLocation(binding.btnSetIP, Gravity.TOP, 0, 0);
                    }
                }, 5000); // 5000 milliseconds = 5 seconds
            }
        });
        binding.backBtn.setOnClickListener((View v) -> {
            finish();
        });

        binding.editTextServerIP.setHint("The current URL: " + chatViewModel.getIp());

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
    private boolean isValidURL(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Patterns.WEB_URL.matcher(url).matches();
        }
        return false;
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
