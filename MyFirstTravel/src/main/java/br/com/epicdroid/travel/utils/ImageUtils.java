package br.com.epicdroid.travel.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static String searchImageByQuery(Context context, Intent data) throws Exception {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String pathImage = cursor.getString(columnIndex);
        cursor.close();

        return pathImage;
    }

    public static Bitmap getBitmapFromFilePath(String url) throws IOException {
        File imgFile = new  File(url);
        if(imgFile.exists()){
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return null;
    }
}
