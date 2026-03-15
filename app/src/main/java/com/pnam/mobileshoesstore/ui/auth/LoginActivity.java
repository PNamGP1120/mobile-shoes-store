package com.pnam.mobileshoesstore.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pnam.mobileshoesstore.R;
import com.pnam.mobileshoesstore.data.model.User;
import com.pnam.mobileshoesstore.data.repository.AuthRepository;
import com.pnam.mobileshoesstore.data.repository.UserRepository;
import com.pnam.mobileshoesstore.ui.admin.AdminDashboardActivity;
import com.pnam.mobileshoesstore.ui.home.HomeActivity;
import com.pnam.mobileshoesstore.utils.RoleConstants;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView txtGoRegister;

    private AuthRepository authRepository;
    private UserRepository userRepository;
    private GoogleSignInClient googleSignInClient;

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() == null) {
                    return;
                }

                try {
                    GoogleSignInAccount account = GoogleSignIn
                            .getSignedInAccountFromIntent(result.getData())
                            .getResult(ApiException.class);

                    if (account != null && account.getIdToken() != null) {
                        firebaseAuthWithGoogle(account.getIdToken());
                    }
                } catch (ApiException e) {
                    Toast.makeText(this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initRepositories();
        initGoogleSignIn();
        initActions();
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        txtGoRegister = findViewById(R.id.txtGoRegister);
    }

    private void initRepositories() {
        authRepository = new AuthRepository();
        userRepository = new UserRepository();
    }

    private void initGoogleSignIn() {
        String webClientId = getString(R.string.default_web_client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initActions() {
        btnLogin.setOnClickListener(v -> loginWithEmail());

        btnGoogleLogin.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        });

        txtGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void loginWithEmail() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (!validateInput(email, password)) {
            return;
        }

        authRepository.loginWithEmail(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadUserProfile(firebaseUser);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(LoginActivity.this, "Login failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        authRepository.signInWithGoogleCredential(
                GoogleAuthProvider.getCredential(idToken, null),
                new AuthRepository.AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser firebaseUser) {
                        if (firebaseUser == null) {
                            Toast.makeText(LoginActivity.this, "Google login failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        createUserProfileIfNeeded(firebaseUser);
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(LoginActivity.this, "Google auth failed: " + message, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void createUserProfileIfNeeded(FirebaseUser firebaseUser) {
        userRepository.getUserById(firebaseUser.getUid(), new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    navigateByRole(user);
                } else {
                    createGoogleUser(firebaseUser);
                }
            }

            @Override
            public void onFailure(String message) {
                createGoogleUser(firebaseUser);
            }
        });
    }

    private void createGoogleUser(FirebaseUser firebaseUser) {
        User user = new User(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "",
                firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "",
                "",
                firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : "",
                RoleConstants.USER,
                true,
                "google",
                System.currentTimeMillis()
        );

        userRepository.createUserProfile(user, new UserRepository.ActionCallback() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(LoginActivity.this, "Create Google profile failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserProfile(FirebaseUser firebaseUser) {
        userRepository.getUserById(firebaseUser.getUid(), new UserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "User profile not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                navigateByRole(user);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(LoginActivity.this, "Load profile failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateByRole(User user) {
        if (RoleConstants.ADMIN.equals(user.getRole())) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finishAffinity();
    }

    private boolean validateInput(String email, String password) {
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

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return false;
        }

        return true;
    }
}