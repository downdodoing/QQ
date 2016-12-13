package com.example.qq.util.jph;

import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.example.qq.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

public class CustomerHelper {
    //一次最大可以选几张
    private final static int LIMIT = 1;
    //剪切工具高度宽度
    private final static int CROPWIDTH = 800;
    private final static int CROPHEIGHT = 800;

    //压缩的图片最大不超过单位B
    private final static int MAXSIZE = 102400;
    //压缩图片的高度宽度
    private final static int ETHEIGHT = 800;
    private final static int ETWDITH = 800;

    public static void onClick(View view, TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (view.getId()) {
            case R.id.album:
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                break;
            case R.id.take_photo:
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            default:
                break;
        }
    }

    private static void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    //压缩
    private static void configCompress(TakePhoto takePhoto) {
        boolean showProgressBar = true;
        boolean enableRawFile = true;
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(MAXSIZE)
                .setMaxPixel(ETWDITH >= ETHEIGHT ? ETWDITH : ETHEIGHT)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private static CropOptions getCropOptions() {
        boolean withWonCrop = true;
        CropOptions.Builder builder = new CropOptions.Builder();

        builder.setOutputX(CROPWIDTH).setOutputY(CROPHEIGHT);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
