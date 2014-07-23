package br.com.epicdroid.travel.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

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

    public static Bitmap getBitmapFromFilePath(String url) {
        File imgFile = new File(url);
        if (imgFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;

            return BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
        }
        return null;
    }

    public static void openImageOnGallery(Context context, String path) {
        Uri pathUri = Uri.fromFile(new File(path));
        Intent openGallery = new Intent(Intent.ACTION_VIEW);
        openGallery.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openGallery.setDataAndTypeAndNormalize(pathUri, Intent.normalizeMimeType("image/png"));
        context.startActivity(openGallery);
    }
}
