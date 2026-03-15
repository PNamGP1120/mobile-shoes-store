package com.pnam.mobileshoesstore.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.pnam.mobileshoesstore.R;
import com.pnam.mobileshoesstore.data.model.User;
import com.pnam.mobileshoesstore.data.repository.AuthRepository;
import com.pnam.mobileshoesstore.data.repository.UserRepository;
import com.pnam.mobileshoesstore.ui.auth.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView txtFullName, txtEmail, txtPhone, txtRole, txtProvider;
    private Button btnLogout;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        initRepositories();
        loadCurrentUserProfile();
        initActions();
    }

    private void initViews() {
        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtRole = findViewById(R.id.txtRole);
        txtProvider = findViewById(R.id.txtProvider);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void initRepositories() {
        authRepository = new AuthRepository();
        userRepository = new UserRepository();
    }

    private void loadCurrentUserProfile() {
        FirebaseUser firebaseUser = authRepository.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
            return;
        }

        userRepository.getUserById(firebaseUser.getUid(), new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    Toast.makeText(HomeActivity.this, "User profile not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                txtFullName.setText("Full name: " + safeText(user.getFullName()));
                txtEmail.setText("Email: " + safeText(user.getEmail()));
                txtPhone.setText("Phone: " + safeText(user.getPhone()));
                txtRole.setText("Role: " + safeText(user.getRole()));
                txtProvider.setText("Provider: " + safeText(user.getProvider()));
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(HomeActivity.this, "Load profile failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String safeText(String value) {
        return value == null || value.trim().isEmpty() ? "N/A" : value;
    }

    private void initActions() {
        btnLogout.setOnClickListener(v -> {
            authRepository.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });
    }
}