package com.pnam.mobileshoesstore.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.pnam.mobileshoesstore.R;
import com.pnam.mobileshoesstore.data.model.User;
import com.pnam.mobileshoesstore.data.repository.AuthRepository;
import com.pnam.mobileshoesstore.data.repository.UserRepository;
import com.pnam.mobileshoesstore.ui.home.HomeActivity;
import com.pnam.mobileshoesstore.utils.RoleConstants;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView txtGoLogin;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initRepositories();
        initActions();
    }

    private void initViews() {
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoLogin = findViewById(R.id.txtGoLogin);
    }

    private void initRepositories() {
        authRepository = new AuthRepository();
        userRepository = new UserRepository();
    }

    private void initActions() {
        btnRegister.setOnClickListener(v -> registerAccount());

        txtGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void registerAccount() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (!validateInput(fullName, email, phone, password, confirmPassword)) {
            return;
        }

        authRepository.registerWithEmail(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(
                        firebaseUser.getUid(),
                        fullName,
                        email,
                        phone,
                        "",
                        RoleConstants.USER,
                        true,
                        "email",
                        System.currentTimeMillis()
                );

                userRepository.createUserProfile(user, new UserRepository.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finishAffinity();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(RegisterActivity.this, "Create profile failed: " + message, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(RegisterActivity.this, "Register failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateInput(String fullName, String email, String phone, String password, String confirmPassword) {
        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError("Full name is required");
            edtFullName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Invalid email");
            edtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Phone is required");
            edtPhone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            edtPassword.setError("Password must be at least 6 characters");
            edtPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Confirm password does not match");
            edtConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}