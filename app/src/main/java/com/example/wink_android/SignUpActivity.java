package com.example.wink_android;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class SignUpActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private String password;
    private ImageView selectedImageView;
    private EditText confirmEditText;
    private EditText displayEditText;
    private Button uploadButton;


    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmEditText = findViewById(R.id.confirmEditText);
        displayEditText = findViewById(R.id.displayEditText);
        ImageView circleImageView = findViewById(R.id.circleImageView);



        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username = usernameEditText.getText().toString().trim();
                    // Check if the username is empty
                    if (username.isEmpty()) {
                        usernameEditText.setBackgroundResource(R.drawable.input_failure);
                    } else {
                        usernameEditText.setBackgroundResource(R.drawable.input_success);
                    }
                }
            }
        });
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    password = passwordEditText.getText().toString().trim();
                    // regular expression that asks for at least one digit and one capital letter, length between 8-16
                    boolean isValidPassword = password.matches("^(?=.*[A-Z])(?=.*\\d).{8,16}$");
                    if (!isValidPassword) {
                        passwordEditText.setBackgroundResource(R.drawable.input_failure);
                    } else {
                        passwordEditText.setBackgroundResource(R.drawable.input_success);
                    }
                }

            }
        });
        confirmEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String confirm = confirmEditText.getText().toString().trim();
                    if (!confirm.equals(password)) {
                        confirmEditText.setBackgroundResource(R.drawable.input_failure);
                    } else {
                        confirmEditText.setBackgroundResource(R.drawable.input_success);
                    }
                }

            }
        });
        displayEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String displayName = displayEditText.getText().toString().trim();
                    if (displayName.isEmpty()) {
                        displayEditText.setBackgroundResource(R.drawable.input_failure);
                    } else {
                        displayEditText.setBackgroundResource(R.drawable.input_success);
                    }
                }

            }
        });
//
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            Uri selectedImageUri = data.getData();
//
//            // Display the selected image in the CircleImageView
//            //circleImageView.setImageURI(selectedImageUri);
//        }
//    }
}




