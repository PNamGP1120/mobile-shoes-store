package com.pnam.mobileshoesstore.data.repository;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.pnam.mobileshoesstore.firebase.FirebaseManager;

public class AuthRepository {

    public interface AuthCallback {
        void onSuccess(FirebaseUser firebaseUser);
        void onFailure(String message);
    }

    public void registerWithEmail(String email, String password, final AuthCallback callback) {
        FirebaseManager.getAuth()
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (callback != null) {
                        callback.onSuccess(authResult.getUser());
                    }
                })
                .addOnFailureListener(e -> {
                    if (callback != null) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    public void loginWithEmail(String email, String password, final AuthCallback callback) {
        FirebaseManager.getAuth()
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (callback != null) {
                        callback.onSuccess(authResult.getUser());
                    }
                })
                .addOnFailureListener(e -> {
                    if (callback != null) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    public void signInWithGoogleCredential(AuthCredential credential, final AuthCallback callback) {
        FirebaseManager.getAuth()
                .signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    if (callback != null) {
                        callback.onSuccess(authResult.getUser());
                    }
                })
                .addOnFailureListener(e -> {
                    if (callback != null) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseManager.getAuth().getCurrentUser();
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public void logout() {
        FirebaseManager.getAuth().signOut();
    }
}