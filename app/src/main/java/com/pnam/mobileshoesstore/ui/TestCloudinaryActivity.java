package com.pnam.mobileshoesstore.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pnam.mobileshoesstore.R;
import com.pnam.mobileshoesstore.cloudinary.CloudinaryConfig;
import com.pnam.mobileshoesstore.cloudinary.CloudinaryUploader;

import java.util.Map;

public class TestCloudinaryActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1001;
    private static final String TAG = "TestCloudinaryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cloudinary);

        Button btnPickImage = findViewById(R.id.btnPickImage);
        btnPickImage.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                uploadImage(imageUri);
            }
        }
    }

    private void uploadImage(Uri imageUri) {
        CloudinaryUploader.uploadImage(
                imageUri,
                CloudinaryConfig.UPLOAD_PRESET,
                new CloudinaryUploader.UploadListener() {

                    @Override
                    public void onStart(String requestId) {
                        Log.d(TAG, "Upload started: " + requestId);
                    }

                    @Override
                    public void onProgress(long bytes, long totalBytes) {
                        Log.d(TAG, "Progress: " + bytes + "/" + totalBytes);
                    }

                    @Override
                    public void onSuccess(Map resultData) {
                        Log.d(TAG, "Upload success: " + resultData);

                        Object secureUrl = resultData.get("secure_url");
                        Object publicId = resultData.get("public_id");

                        Log.d(TAG, "secure_url = " + secureUrl);
                        Log.d(TAG, "public_id = " + publicId);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e(TAG, "Upload error: " + errorMessage);
                    }

                    @Override
                    public void onReschedule(String requestId) {
                        Log.d(TAG, "Upload rescheduled: " + requestId);
                    }
                }
        );
    }
}