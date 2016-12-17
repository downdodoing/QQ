package com.example.qq.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.MyApplication;
import com.example.qq.R;
import com.jph.takephoto.app.TakePhotoActivity;

import java.io.ByteArrayOutputStream;

public abstract class BaseActivity extends TakePhotoActivity {
    private boolean isValid;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    public abstract void initView(Bundle savedInstanceState);

    /*
    * colorB:点击之前的颜色
    * colorE:点击之后的颜色
    * v instanceof TextView/Button都为true
    * */
    public void setOnTouch(final View v, final int colorE, final int colorB, final Activity activity, final boolean isValid1) {
        isValid = isValid1;
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int id = v.getId();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (v instanceof Button) {
                            if (isValid) {
                                ((Button) v).setBackgroundResource(colorE);
                            }
                        } else if (v instanceof TextView) {
                            ((TextView) v).setTextColor(colorE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (v instanceof Button) {
                            if (isValid) {
                                ((Button) v).setBackgroundResource(colorB);
                            }
                        } else if (v instanceof TextView) {
                            ((TextView) v).setTextColor(colorB);
                        }
                        if (id == R.id.log_in_bnt) {
                            login();
                        } else if (id == R.id.show_service) {
                            Intent intent = new Intent(activity, ShowService.class);
                            startActivity(intent);
                        } else if (id == R.id.register) {
                            Intent intent = new Intent(activity, PhoneNum.class);
                            startActivity(intent);
                        } else if (id == R.id.next && isValid) {
                            intentToNext();
                        } else if (id == R.id.get_verifaction) {
                            afterClickgetVerifaction();
                            //注册页面
                        } else if (id == R.id.login_bnt) {
                            MyApplication.removeActitivy(2);
                        } else if (id == R.id.album || id == R.id.take_photo) {
                            getPhoto(v);
                        } else if (id == R.id.register_bnt) {
                            register();
                            //注册成功页面
                        } else if (id == R.id.r_s_login) {
                            registerSuccess();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void login() {
    }

    public void registerSuccess() {
    }

    public void register() {

    }

    public void getPhoto(View view) {
    }

    //空方法声明,在子类中重写
    public void afterClickgetVerifaction() {
    }

    //跳转到下一个页面
    public void intentToNext() {
    }

    //将bitmap转化为二进制
    public byte[] getByteBitmap(Bitmap bitmap) {
        byte[] compressData = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        compressData = outputStream.toByteArray();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return compressData;
    }

    //将二进制转化为bitmap
    public Bitmap getBitmapByByte(byte[] data) {
        if (null != data && data.length > 0) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return null;
    }

    public void showToast(Activity activity, String hintStr) {
        Toast.makeText(activity, hintStr, Toast.LENGTH_SHORT).show();
    }

    //判断是否连网
    public boolean isConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    //设置加载进度条
    public void setProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    //判断输入框是否输入数据
    public boolean isNull(String string) {
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
