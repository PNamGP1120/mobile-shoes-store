package com.pnam.mobileshoesstore.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pnam.mobileshoesstore.data.model.User;
import com.pnam.mobileshoesstore.firebase.FirebaseManager;
import com.pnam.mobileshoesstore.utils.FirestoreCollections;

public class UserRepository {

    public interface UserCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }

    public interface ActionCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public void createUserProfile(User user, final ActionCallback callback) {
        FirebaseManager.getFirestore()
                .collection(FirestoreCollections.USERS)
                .document(user.getUid())
                .set(user.toMap())
                .addOnSuccessListener(unused -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    public void getUserById(String uid, final UserCallback callback) {
        FirebaseManager.getFirestore()
                .collection(FirestoreCollections.USERS)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful()) {
                            if (callback != null) {
                                callback.onFailure(task.getException() != null
                                        ? task.getException().getMessage()
                                        : "Load user failed");
                            }
                            return;
                        }

                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            User user = document.toObject(User.class);
                            if (user != null) {
                                user.setUid(document.getId());
                            }
                            if (callback != null) callback.onSuccess(user);
                        } else {
                            if (callback != null) callback.onFailure("User not found");
                        }
                    }
                });
    }

    public void updateUserProfile(User user, final ActionCallback callback) {
        FirebaseManager.getFirestore()
                .collection(FirestoreCollections.USERS)
                .document(user.getUid())
                .update(user.toMap())
                .addOnSuccessListener(unused -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }
}