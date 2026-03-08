package com.pnam.mobileshoesstore.data.repository;

import com.pnam.mobileshoesstore.data.model.User;

public class UserRepository {

    public UserRepository() {
    }

    public void createUserProfile(User user) {
        // TODO: Save user to Firestore in later phase
    }

    public void getUserById(String uid) {
        // TODO: Read user from Firestore
    }

    public void updateUserProfile(User user) {
        // TODO: Update user document
    }

    public void updateUserRole(String uid, String role) {
        // TODO: Update role field
    }

    public void updateUserActiveStatus(String uid, boolean active) {
        // TODO: Lock/unlock user
    }

    public void deleteUser(String uid) {
        // TODO: Delete user if needed
    }
}