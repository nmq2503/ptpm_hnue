package com.nmq.foodninjaver2.repositories;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MainRepository {
    private Context context;

    // Constructor để nhận Context
    public MainRepository(Context context) {
        this.context = context;
    }

    public String getRealPathFromURI(Uri uri) {
        String filePath = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }
}
