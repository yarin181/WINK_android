package com.example.wink_android.activities;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.example.wink_android.DB.ChatDB;
//import com.example.wink_android.Manifest;
import android.Manifest;
//import com.example.wink_android.Manifest;
import com.example.wink_android.R;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.general.Constants;
import com.example.wink_android.view.ChatViewModel;


import java.util.Objects;

public class Login extends AppCompatActivity {
    private ChatViewModel viewModel;
    private EditText editTextName;
    private EditText editTextPassword;
    private String enteredUserName;
    private Button loginBtn,registerBtn;
    private ImageButton settingsBtn;
    private  static boolean booleanIsFirstLunch = true;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ChatDB.getInstance(this);

        viewModel=new ChatViewModel();
        viewModel.loadSettings();
        setTheme();
        super.onCreate(savedInstanceState);
        if(booleanIsFirstLunch){
            booleanIsFirstLunch = false;
            if(viewModel.getConnectUser()!= null){
                finish();
                viewModel.setToken(viewModel.getConnectUser().getToken());
                Intent i = new Intent(Login.this, UsersActivity.class);
                i.putExtra("connected",true);
                startActivity(i);
            }else {
                viewModel.deleteUserDetails();
            }
        }


        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextText1);
        editTextPassword = findViewById(R.id.editTextTextPassword1);
        loginBtn = findViewById(R.id.button);
        registerBtn=findViewById(R.id.button2);
        settingsBtn = findViewById(R.id.settingsButtonLogin);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            String params[]={Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(this,params,1);
        }
        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextName.setBackgroundResource(R.drawable.input_background);
                }

            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPassword.setBackgroundResource(R.drawable.input_background);
                }

            }
        });

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
                viewModel.tryToLogin(name,password);

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
                showAlert("The password or the username is incorrect");
            }
        });



        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUpActivity.class);
            startActivity(intent);
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void askForAccess(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            String params[]={Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(this,params,1);
        }
    }
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        viewModel.deleteUserDetails();
//        setTheme();
//    }

    private void setTheme() {
        boolean isDarkMode = viewModel.getTheme();
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

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


