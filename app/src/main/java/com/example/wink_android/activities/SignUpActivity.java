package com.example.wink_android.activities;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;

import com.example.wink_android.R;
import com.example.wink_android.activities.popupsActivities.SettingsActivity;
import com.example.wink_android.general.Constants;
import com.example.wink_android.general.OvalImageDrawable;
import com.example.wink_android.requests.RegisterRequest;
import com.example.wink_android.view.ChatViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity {



    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmEditText;
    private ImageButton settingsBtn;
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
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel=new ChatViewModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditText);
        settingsBtn = findViewById(R.id.settingsBtn);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmEditText = findViewById(R.id.confirmEditText);
        displayEditText = findViewById(R.id.displayEditText);
        circleImageView = findViewById(R.id.circleImageView);
        lonInBtn = findViewById(R.id.logIn);
        signUpBtn = findViewById(R.id.signUp);
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
                else{
                    usernameEditText.setBackgroundResource(R.drawable.input_background);
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordEditText.setBackgroundResource(R.drawable.input_background);
                    showAlert("The password must contain:\n" + "1. At least one capital letter.\n"
                            + "2. At least one digit.\n" +  "3. Length of 8-16 characters.");

                } else {
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


        confirmEditText.setOnFocusChangeListener((v, hasFocus) -> {
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
            else{
                confirmEditText.setBackgroundResource(R.drawable.input_background);
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
                else{
                    displayEditText.setBackgroundResource(R.drawable.input_background);
                }

            }
        });
        circleImageView.setOnClickListener(view -> {
            openImageSelectionOptions();
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
            if(!isProfilePic){
               circleImageView.setBackgroundResource(R.drawable.lack_circle_image);
            }

            // only when all the fields are full
            if(isConfirm && isPassword && isDisplayName && isUsername && isProfilePic){
                //todo -  enter to the data base here
                registerRequest.setUsername(username);
                registerRequest.setPassword(password);
                registerRequest.setDisplayName(displayName);
                viewModel.tryToRegister(registerRequest);
            }

        });


        viewModel.getStatus().observe(this, v-> {
            if (Objects.equals(v, Constants.FAILED_REGISTER)) {
                showAlert("username already exist....");
                // clear fields
                usernameEditText.setText("");
                passwordEditText.setText("");
                confirmEditText.setText("");
                displayEditText.setText("");
                circleImageView.setImageResource(R.drawable.circle_image);
            } else if (Objects.equals(v, Constants.FAILED_CONNECT_TO_SERVER)) {
                showAlert("connection to server failed....");
            } else if (Objects.equals(v, Constants.SUCCESSFUL_REGISTER)) {
                finish();
            }

        });

        settingsBtn.setOnClickListener(v-> {
            Intent intent = new Intent(SignUpActivity.this, SettingsActivity.class);
            startActivity(intent);
            viewModel.editSettings();
        });

    }
    private void openImageSelectionOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    openGallery();
                    break;
                case 1:
                    openCamera();
                    break;
            }
        });
        builder.show();
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Bitmap imageBitmap = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    imageBitmap = (Bitmap) extras.get("data");
                }
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                Uri selectedImageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    imageBitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (imageBitmap != null) {
                // Create a circular bitmap
                Bitmap circularBitmap = createCircularBitmap(imageBitmap);

                circleImageView.setImageBitmap(circularBitmap);

                // Convert bitmap to byte array and then to base 64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                circularBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String profilePic = Base64.encodeToString(byteArray, Base64.DEFAULT);
                registerRequest.setProfilePic(profilePic);

                isProfilePic = true;
            }
        }
    }
    private Bitmap createCircularBitmap(Bitmap bitmap) {
        int diameter = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap circularBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circularBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        float radius = diameter / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        return circularBitmap;
    }

    private void showAlert(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup, null);
        EditText editText = dialogView.findViewById(R.id.text); // Replace with your actual EditText ID
        editText.setText(errorMessage); // Set the error message text here
        builder.setView(dialogView)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    // Perform any necessary action on positive button click
                    dialogInterface.dismiss();
                })
                .setCancelable(true)
                .show();
        viewModel.setInitialStatus();
    }

}





