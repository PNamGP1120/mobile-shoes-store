package com.pnam.mobileshoesstore.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.pnam.mobileshoesstore.R;
import com.pnam.mobileshoesstore.data.repository.AuthRepository;
import com.pnam.mobileshoesstore.ui.auth.LoginActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView txtTitle;
    private Button btnLogout;

    private AuthRepository authRepository;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        initViews();
        initRepositories();
        initGoogleSignIn();
        initActions();
    }

    private void initViews() {
        txtTitle = findViewById(R.id.txtTitle);
        btnLogout = findViewById(R.id.btnLogout);

        txtTitle.setText("Admin Dashboard");
    }

    private void initRepositories() {
        authRepository = new AuthRepository();
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initActions() {
        btnLogout.setOnClickListener(v -> logoutAdmin());
    }

    private void logoutAdmin() {
        authRepository.logout();

        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Toast.makeText(AdminDashboardActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}