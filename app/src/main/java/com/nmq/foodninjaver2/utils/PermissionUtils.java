package com.nmq.foodninjaver2.utils;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;

import android.Manifest;

public class PermissionUtils {
    public static final int REQUEST_PICK_IMAGE = 1; // Mã request cho thư viện
    public static final int REQUEST_TAKE_PHOTO = 2; // Mã request cho camera

    public static void requestCameraPermissions(Activity activity, ActivityResultLauncher<String[]> launcher) {
        String[] REQUIRED_PERMISSIONS = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        launcher.launch(REQUIRED_PERMISSIONS);
    }
}
