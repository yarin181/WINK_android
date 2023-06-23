package com.example.wink_android.activities;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.R;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.general.Constants;
import com.example.wink_android.repository.ChatRepository;
import com.example.wink_android.view.ChatViewModel;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private ChatViewModel viewModel;
    private EditText editTextName;
    private EditText editTextPassword;
    private String enteredUserName;
    private String fireBaseToken;
    private Button loginBtn,registerBtn;
    private ImageButton settingsBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ChatDB.getInstance(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken= instanceIdResult.getToken();
            fireBaseToken=newToken;
        });
        viewModel=new ChatViewModel();
        viewModel.loadSettings();
        setTheme();
        super.onCreate(savedInstanceState);

        if(viewModel.getConnectUser()!= null){
            viewModel.setToken(viewModel.getConnectUser().getToken());
            Intent i = new Intent(Login.this, UsersActivity.class);
            i.putExtra("connected",true);
            startActivity(i);
        }else {
            viewModel.deleteUserDetails();
        }

        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextText1);
        editTextPassword = findViewById(R.id.editTextTextPassword1);
        loginBtn = findViewById(R.id.button);
        registerBtn=findViewById(R.id.button2);
        settingsBtn = findViewById(R.id.settingsButtonLogin);



        settingsBtn.setOnClickListener(v-> {

            Intent intent = new Intent(Login.this, SettingsActivity.class);
            startActivity(intent);
            viewModel.editSettings();
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                editTextName.setText("");
                editTextPassword.setText("");
                enteredUserName = name;
                viewModel.tryToLogin(name,password,fireBaseToken);

            }
        });
        viewModel.getStatus().observe(this, v->{
            if (Objects.equals(v, Constants.FAILED_CONNECT_TO_SERVER)) {
                showAlert(Constants.FAILED_CONNECT_TO_SERVER);
            } else if(Objects.equals(v, "exist")){
                Intent i = new Intent(Login.this, UsersActivity.class);
                i.putExtra("nameFromLogin",enteredUserName);
                startActivity(i);
            }else if(Objects.equals(v, Constants.NOT_EXIST)) {
                // Initialize the popup layout
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // Create the popup window
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                View popupView = inflater.inflate(R.layout.popup_incorrect_details, null);
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                editTextName.setBackgroundResource(R.drawable.input_failure);
                editTextPassword.setBackgroundResource(R.drawable.input_failure);

                new Handler().postDelayed(() -> {
                    //Show popup of incorrect username or password
                    popupWindow.showAtLocation(loginBtn, Gravity.TOP, 0, 0);
                }, 500);
            }
        });



        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUpActivity.class);
            startActivity(intent);
        });



    }
    @Override
    protected void onRestart() {
        super.onRestart();
        viewModel.deleteUserDetails();
        setTheme();
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    viewModel.setInitialStatus();
    }
}


