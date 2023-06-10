package com.example.wink_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.wink_android.DB.ChatDB;
import com.example.wink_android.DB.User;
import com.example.wink_android.DB.UserDao;
import com.example.wink_android.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AppCompatActivity {

    private ActivityAddUserBinding binding;
    private ChatDB db;
    private User user;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 700;
        params.height = 500;
        getWindow().setAttributes(params);

        db = Room.databaseBuilder(getApplicationContext(), ChatDB.class, "ChatDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();
        handleSave();
//        Button submitButton = binding.submitButton;
//        final EditText usernameEditText = binding.usernameEditText;
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = usernameEditText.getText().toString();
//
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("username", username);
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
//            }
//        });
    }
    private void handleSave() {
        binding.addBtn.setOnClickListener(view -> {
            String username = binding.usernameEt.getText().toString();
            user = new User(username);
            Log.d("pop : ",username);
            userDao.insertUser(user);
            finish();
        });
    }
}
