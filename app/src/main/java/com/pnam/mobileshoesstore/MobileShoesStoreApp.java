package com.pnam.mobileshoesstore;

import android.app.Application;

import com.pnam.mobileshoesstore.cloudinary.CloudinaryManager;

public class MobileShoesStoreApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CloudinaryManager.init(this);
    }
}