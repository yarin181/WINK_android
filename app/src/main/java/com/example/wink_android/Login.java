package com.example.wink_android;
import android.content.Context;
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
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wink_android.api.ApiRequests;

public class Login extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private Button loginBtn,registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextText1);
        editTextPassword = findViewById(R.id.editTextTextPassword1);
        loginBtn = findViewById(R.id.button);
        registerBtn=findViewById(R.id.button2);


        // Initialize the popup layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_already_exist, null);
        // Create the popup window
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = editTextName.getText().toString();
                //String password = editTextPassword.getText().toString();
                //ApiRequests temp = new ApiRequests();
                //temp.getToken(name,password);
                // Use the name and password variables as needed
                Intent intent = new Intent(Login.this, UsersActivity.class);
                startActivity(intent);

                //if the username already exist in the database (ask yoav)
               /*


                if(true){
                   // Show the popup when the EditText gains focus
                   popupWindow.showAtLocation(loginBtn, Gravity.TOP, 0, 0);
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           popupWindow.dismiss();
                       }
                   }, 5000); // 5000 milliseconds = 5 seconds
               }*/
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
