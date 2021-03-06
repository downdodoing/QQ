package com.example.qq.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.*;
import android.os.Message;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.qq.MyApplication;
import com.example.qq.R;
import com.example.qq.activity.interfaceV.IRegister;
import com.example.qq.entity.Param;
import com.example.qq.entity.User;
import com.example.qq.presenter.UserP;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.util.circleImage.GlideCircleTransform;
import com.example.qq.util.jph.CustomerHelper;
import com.jph.takephoto.model.TResult;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Register extends BaseActivity implements IRegister {
    private Register activity;

    private TextView title, login_bnt, back, album, take_photo, phonNum, set_photo;

    private Spinner spinner;

    private ImageView head_photo;

    private EditText netName, age;

    private Button register_bnt;

    private LinearLayout change_head_photo;

    private String sex = "男";
    private boolean isShow;//用于存储是否已经点击设置头像
    private byte[] compress_head_photo = null;
    private String mPhoneNum;
    //用于存储后台返回的数据
    private String result;

    private MyHandler handler;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.register);
        MyApplication.addActivity(this);
        init();
        forwardData();
    }

    @Override
    public void forwardData() {
        Intent intent = activity.getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");
        phonNum.setText(mPhoneNum);
    }

    @Override
    public void init() {
        activity = this;

        handler = new MyHandler();

        progressBar = (RelativeLayout) findViewById(R.id.progress_bar);
        title = (TextView) findViewById(R.id.title_text);
        back = (TextView) findViewById(R.id.back);
        login_bnt = (TextView) findViewById(R.id.login_bnt);
        album = (TextView) findViewById(R.id.album);
        take_photo = (TextView) findViewById(R.id.take_photo);
        phonNum = (TextView) findViewById(R.id.phonNum);
        set_photo = (TextView) findViewById(R.id.set_photo);

        spinner = (Spinner) findViewById(R.id.sex);

        head_photo = (ImageView) findViewById(R.id.head_photo);

        netName = (EditText) findViewById(R.id.netName);
        age = (EditText) findViewById(R.id.age);

        register_bnt = (Button) findViewById(R.id.register_bnt);

        change_head_photo = (LinearLayout) findViewById(R.id.change_head_photo);

        back.setOnClickListener(activity);
        head_photo.setOnClickListener(activity);
        album.setOnClickListener(this);
        title.setText("QQ号注册");
        //设置选择性别下拉框
        spinner();
        setOnTouch();
    }

    @Override
    public void spinner() {
        String[] mItems = getResources().getStringArray(R.array.spinner_a);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mItems);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) {
                    sex = "男";
                } else {
                    sex = "女";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setOnTouch() {
        this.setOnTouch(register_bnt, R.drawable.bnt_back_down, R.drawable.bnt_back_up, activity, true);
        this.setOnTouch(login_bnt, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), activity, true);
        this.setOnTouch(album, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), activity, true);
        this.setOnTouch(take_photo, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), activity, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                activity.finish();
                break;
            case R.id.head_photo:
                if (!isShow) {
                    showSelect();
                } else {
                    hiddeSelect();
                }
                break;
            default:
                break;
        }
    }

    //从相册中选取图片
    @Override
    public void getPhoto(View view) {
        CustomerHelper.onClick(view, this.getTakePhoto());
        hiddeSelect();
    }

    //获取图片后的回调函数
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String path = result.getImages().get(0).getCompressPath();
        Glide.with(this).load(new File(path)).transform(new GlideCircleTransform(this)).
                into(head_photo);
        set_photo.setVisibility(View.GONE);

        getHeadPhoto(path);
    }

    //获取头像
    public void getHeadPhoto(String path) {
        //获取手机中的图片
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //清空画图缓存否则下次获取图片时还是原图片
        if (null != bitmap) {
            //对图片进行压缩，100为不压缩，并写入字节流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        }
        //获取图片的二进制
        compress_head_photo = bos.toByteArray();
        try {
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void register() {
        if (progressBar.getVisibility() == View.GONE) {
            String mNetName = netName.getText().toString();
            String mAge = age.getText().toString();
            if (isNull(mNetName)) {
                this.showToast(this, "请输入昵称");
            } else if (isNull(mAge)) {
                this.showToast(this, "请输入年龄");
            } else if (this.isConnect()) {
                this.setProgressBar();
                setDefaultHeadPhoto();
                User user = new User(mNetName, mAge, Base64.encodeToString(compress_head_photo, Base64.DEFAULT), sex, mPhoneNum);
                intentData(user);
            } else {
                this.showToast(this, "请检查网络");
            }
        }
    }

    //将数据传递到后台
    public void intentData(User user) {
        JSONObject joo = (JSONObject) JSON.toJSON(user);
        UserP userP = new UserP();
        userP.setSetDataListener(new ISetDataListener() {
            @Override
            public void failed(Exception io) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void success(Response response) {
                try {
                    String result = response.body().string();
                    JSONObject jooo = JSON.parseObject(result);
                    successGetData(jooo);
                    Register.this.setProgressBar();
                } catch (Exception ex) {

                }
            }
        });
        userP.saveUser(new Param("user", joo.toJSONString()));
    }

    //获取数据成功后
    public void successGetData(JSONObject jooo) {
        MyApplication.removeActitivy(2);
        String name = jooo.getString("name");
        //跳转到注册成功页面
        Intent intent = new Intent(Register.this, RegisterSuccess.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    //判断是否设置头像，若没有则设置默认头像
    public void setDefaultHeadPhoto() {
        if (null == compress_head_photo) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fit);
            if (null != bitmap) {
                compress_head_photo = getByteBitmap(bitmap);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow) {
                hiddeSelect();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //设置头像
    @Override
    public void showSelect() {
        isShow = true;
        Animation translate_in = AnimationUtils.loadAnimation(this, R.anim.translate_in);
        setAnimationOption(translate_in);
    }

    @Override
    public void hiddeSelect() {
        isShow = false;
        Animation translate_out = AnimationUtils.loadAnimation(this, R.anim.translate_out);
        setAnimationOption(translate_out);
    }

    private void setAnimationOption(Animation animation) {
        if (isShow) {
            change_head_photo.setVisibility(View.VISIBLE);
        } else {
            change_head_photo.setVisibility(View.GONE);
        }
        animation.setDuration(500);
        animation.setFillAfter(true);
        change_head_photo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                change_head_photo.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (0 == msg.what) {
                activity.setProgressBar();
                activity.showToast(Register.this, "注册失败！");
            }
        }
    }
}
