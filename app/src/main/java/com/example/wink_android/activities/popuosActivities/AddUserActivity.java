package com.example.wink_android.activities.popuosActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
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
        params.height = 500;
        getWindow().setAttributes(params);

        resultIntent = new Intent();

        binding.addBtn.setOnClickListener(view -> {
            if (binding.usernameEt.getText().toString().length() > 0) {
                resultIntent.putExtra("username", binding.usernameEt.getText().toString());
                setResult(AddUserActivity.RESULT_OK, resultIntent);
                this.finish();
            } else {
                (Toast.makeText(this, "Not a Valid username", Toast.LENGTH_LONG)).show();
            }
        });
    }
}
