package com.example.wink_android.activities.popupsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wink_android.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AppCompatActivity {
    private ActivityAddUserBinding binding;
    private Intent resultIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 700;
        params.height = 800;
        getWindow().setAttributes(params);
        resultIntent = new Intent();

        binding.usernameEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    addContact();
                    return true; // Return true to indicate that the event has been handled
                }
                return false; // Return false if the event is not handled
            }
        });


        binding.addBtn.setOnClickListener(view -> {
            addContact();
        });
        binding.closeBtn.setOnClickListener(view -> {
            finish();
        });
    }
    private void addContact() {
        if (binding.usernameEt.getText().toString().length() > 0) {
            resultIntent.putExtra("username", binding.usernameEt.getText().toString());
            setResult(AddUserActivity.RESULT_OK, resultIntent);
            this.finish();
        } else {
            (Toast.makeText(this, "Not a Valid username", Toast.LENGTH_LONG)).show();
        }
    }
}