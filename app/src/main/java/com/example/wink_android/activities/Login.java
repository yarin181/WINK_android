package com.example.wink_android.activities;
import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wink_android.DB.Chat;
import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.R;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.view.ChatViewModel;

import java.util.Objects;

public class Login extends AppCompatActivity {
private ChatViewModel viewModel;
    private EditText editTextName;
    private EditText editTextPassword;
    private String enteredUserName;
    private Button loginBtn,registerBtn;
    private ImageButton settingsBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ChatDB.getInstance(this);
        viewModel=new ChatViewModel();
//        viewModel.deleteUserDetails();
        if(viewModel.getConnectUser()!= null){
            viewModel.setToken(viewModel.getConnectUser().getToken());
            Intent i = new Intent(Login.this, UsersActivity.class);
            i.putExtra("connected",true);
            startActivity(i);
        }else {
            viewModel.deleteUserDetails();
        }


        editTextName = findViewById(R.id.editTextText1);
        editTextPassword = findViewById(R.id.editTextTextPassword1);
        loginBtn = findViewById(R.id.button);
        registerBtn=findViewById(R.id.button2);
        settingsBtn = findViewById(R.id.settingsButtonLogin);


        // Initialize the popup layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_already_exist, null);
        // Create the popup window
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        settingsBtn.setOnClickListener(v-> {
            Intent intent = new Intent(Login.this, SettingsActivity.class);
            startActivity(intent);
            Log.i("UsersActivity" ,"settings");
            viewModel.editSettings();
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextName.getText().toString();
                enteredUserName=name;
                String password = editTextPassword.getText().toString();
                viewModel.tryToLogin(name,password);


                /*

                ApiRequests temp = new ApiRequests();
//                temp.getToken(name,password);
//                temp.getMyUserData(name,"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImExIiwiaWF0IjoxNjg2NDkwMDA4fQ.gFRRSuAX2PW2eQqKExjTEh6pbK1OGF397_-823RKBhs");
//               temp.registerUser(name,password,name,"1");
//                temp.getFriends("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImExIiwiaWF0IjoxNjg2NDkwMDA4fQ.gFRRSuAX2PW2eQqKExjTEh6pbK1OGF397_-823RKBhs");
//              temp.addFriend(name,"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImExIiwiaWF0IjoxNjg2NDkwMDA4fQ.gFRRSuAX2PW2eQqKExjTEh6pbK1OGF397_-823RKBhs");
//               temp.addMessage(1,"msg from app","bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImExIiwiaWF0IjoxNjg2NDkwMDA4fQ.gFRRSuAX2PW2eQqKExjTEh6pbK1OGF397_-823RKBhs");
//               temp.getMessages(1,"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImExIiwiaWF0IjoxNjg2NDkwMDA4fQ.gFRRSuAX2PW2eQqKExjTEh6pbK1OGF397_-823RKBhs");
                // Use the name and password variables as needed
                Intent intent = new Intent(Login.this, UsersActivity.class);
                startActivity(intent);

                //if the username already exist in the database (ask yoav)
               if(true){

               }*/
            }
        });
        viewModel.getStatus().observe(this, v->{
            if(Objects.equals(v, "exist")){
                Intent i = new Intent(Login.this, UsersActivity.class);
                i.putExtra("nameFromLogin",enteredUserName);
                startActivity(i);
            }else{
//                // Show the popup when the EditText gains focus
//                popupWindow.showAtLocation(loginBtn, Gravity.TOP, 0, 0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        popupWindow.dismiss();
//                    }
//                }, 5000); // 5000 milliseconds = 5 seconds
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
