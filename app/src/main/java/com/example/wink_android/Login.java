package com.example.wink_android;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.i("Login",name);
                Log.i("Login",password);
                ApiRequests temp = new ApiRequests();
                temp.getToken(name,password);
                // Use the name and password variables as needed
                Intent intent = new Intent(Login.this, UsersActivity.class);
                startActivity(intent);
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
