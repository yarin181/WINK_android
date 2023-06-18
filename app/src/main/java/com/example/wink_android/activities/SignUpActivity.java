package com.example.wink_android.activities;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wink_android.R;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.requests.RegisterRequest;
import com.example.wink_android.view.ChatViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmEditText;
    private EditText displayEditText;
    private Button lonInBtn;
    private Button signUpBtn;
    private ImageView circleImageView;
    private boolean isProfilePic = false;

    private String password;
    private boolean isPassword = false;

    private String confirm;
    private boolean isConfirm = false;
    private String username;
    private boolean isUsername = false;
    private String displayName;
    private boolean isDisplayName= false;

    private RegisterRequest registerRequest;
    private ChatViewModel viewModel;




    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();

                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(selectedImageUri);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // Convert bitmap to byte array and the to base 64
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String profilePic = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    registerRequest.setProfilePic(profilePic);

                    circleImageView.setImageDrawable(new OvalImageDrawable(bitmap));
                    isProfilePic = true;

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmEditText = findViewById(R.id.confirmEditText);
        displayEditText = findViewById(R.id.displayEditText);
        circleImageView = findViewById(R.id.circleImageView);
        lonInBtn = findViewById(R.id.logIn);
        signUpBtn = findViewById(R.id.signUp);
        viewModel=new ChatViewModel();


        registerRequest=new RegisterRequest();



        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    username = usernameEditText.getText().toString().trim();
                    // Check if the username is empty
                    if (username.isEmpty()) {
                        usernameEditText.setBackgroundResource(R.drawable.input_failure);
                        isUsername = false;
                    } else {
                        usernameEditText.setBackgroundResource(R.drawable.input_success);
                        isUsername = true;
                    }

                }
            }
        });

        // Initialize the popup layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_password, null);

        // Create the popup window
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Show the popup when the EditText gains focus
                    popupWindow.showAtLocation(passwordEditText, Gravity.TOP, 0, 0);
                    // Schedule the dismissal of the popup after 5 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.dismiss();
                        }
                    }, 5000); // 5000 milliseconds = 5 seconds
                } else {
                    // Dismiss the popup when the EditText loses focus
                    popupWindow.dismiss();
                    password = passwordEditText.getText().toString().trim();
                    // regular expression that asks for at least one digit and one capital letter, length between 8-16
                    boolean isValidPassword = password.matches("^(?=.*[A-Z])(?=.*\\d).{8,16}$");
                    if (!isValidPassword) {
                        passwordEditText.setBackgroundResource(R.drawable.input_failure);
                        isPassword = false;
                    } else {
                        passwordEditText.setBackgroundResource(R.drawable.input_success);
                        isPassword = true;
                    }
                }
            }
        });


        confirmEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirm = confirmEditText.getText().toString().trim();
                    if (!confirm.equals(password) || confirm.isEmpty()) {
                        confirmEditText.setBackgroundResource(R.drawable.input_failure);
                        isConfirm = false;
                    } else {
                        confirmEditText.setBackgroundResource(R.drawable.input_success);
                        isConfirm = true;
                    }
                }

            }
        });
        displayEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    displayName = displayEditText.getText().toString().trim();
                    if (displayName.isEmpty()) {
                        displayEditText.setBackgroundResource(R.drawable.input_failure);
                        isDisplayName = false;
                    } else {
                        displayEditText.setBackgroundResource(R.drawable.input_success);
                        isDisplayName = true;
                    }
                }

            }
        });
        circleImageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });
        lonInBtn.setOnClickListener(v -> {
            this.finish();
        });
        signUpBtn.setOnClickListener(v -> {

            // loss focus from all the editText
            usernameEditText.getOnFocusChangeListener().onFocusChange(usernameEditText, false);
            passwordEditText.getOnFocusChangeListener().onFocusChange(passwordEditText, false);
            confirmEditText.getOnFocusChangeListener().onFocusChange(confirmEditText, false);
            displayEditText.getOnFocusChangeListener().onFocusChange(displayEditText, false);

            // only when all the fields are full
            if(isConfirm && isPassword && isDisplayName && isUsername && isProfilePic){
                //todo -  enter to the data base here
            registerRequest.setUsername(username);
            registerRequest.setPassword(password);
            registerRequest.setDisplayName(displayName);
           // registerRequest.setProfilePic();
            viewModel.tryToRegister(registerRequest);
            }

        });


        viewModel.getStatus().observe(this, v->{
            if(Objects.equals(v, "exist")){
                View existPopupView = inflater.inflate(R.layout.popup_already_exist, null);
                PopupWindow existPopupWindow = new PopupWindow(existPopupView, width, height, true);

                //Show popup of incorrect username or password
                existPopupWindow.showAtLocation(signUpBtn, Gravity.TOP, 0, 0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        existPopupWindow.dismiss();
                    }
                }, 5000); // 5000 milliseconds = 5 seconds
            }else{
                finish();
            }
        });

    }
}




