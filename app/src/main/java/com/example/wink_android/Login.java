package com.example.wink_android;
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
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextText1);
        editTextPassword = findViewById(R.id.editTextTextPassword1);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.i("Login",name);
                Log.i("Login",password);
                ApiRequests temp = new ApiRequests();
                temp.getToken(name,password);
                // Use the name and password variables as needed
            }
        });
    }
}
