package com.nmq.foodninjaver2.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.utils.ValidateFunction;

public class RegisterProcessActivity extends AppCompatActivity {

    ImageView ivBackBtn;
    EditText edtFirstNameUser, edtLastNameUser, edtPhoneNumberUser;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_process);

        initViews();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(v -> {
            if (validateInputs()) {
                nextScreen();
            }
        });
    }

    private void initViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        edtFirstNameUser = findViewById(R.id.edtFirstNameUser);
        edtLastNameUser = findViewById(R.id.edtLastNameUser);
        edtPhoneNumberUser = findViewById(R.id.edtPhoneNumberUser);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void nextScreen() {
        Intent intent = new Intent(RegisterProcessActivity.this, UploadPhotoActivity.class);

        intent.putExtra("user_name", getIntent().getStringExtra("user_name"));
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("password", getIntent().getStringExtra("password"));

        intent.putExtra("first_name", edtFirstNameUser.getText().toString());
        intent.putExtra("last_name", edtLastNameUser.getText().toString());
        intent.putExtra("phone_number", edtPhoneNumberUser.getText().toString());

        startActivity(intent);
    }

    private boolean validateInputs() {
        String firstName = edtFirstNameUser.getText().toString().trim();
        String lastName = edtLastNameUser.getText().toString().trim();
        String phoneNumber = edtPhoneNumberUser.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ValidateFunction.validatePhoneNumber(phoneNumber)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}