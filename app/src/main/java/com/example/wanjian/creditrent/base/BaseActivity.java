package com.example.wanjian.creditrent.base;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Soully on 2017/7/25.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上可用
         * 将状态栏颜色改变
         * 沉浸式请看郭霖的：http://blog.csdn.net/guolin_blog/article/details/51763825
         * 即 onWindowFocusChanged（）
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintResource(color);
            tintManager.setStatusBarTintEnabled(true);
        }
    }
/**    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );
//        }
//    }
    /*
    获取图片的主色调
    id为本地图片的地址 如：R.mipmap.ic_launcher
     */
    public int  GetPaletteAyNc(int id){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        final int[] color_GRB = new int[1];
        new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
            Palette.Swatch s1,s2,s3,s4,s5,s6;
            @Override
            public void onGenerated(Palette palette) {
                 s1 = palette.getVibrantSwatch();
                 s2 = palette.getDarkVibrantSwatch();
                 s3 = palette.getLightVibrantSwatch();
                 s4 = palette.getMutedSwatch();
                 s5 = palette.getDarkMutedSwatch();
                 s6 = palette.getLightMutedSwatch();
                if (s1 != null) {
                    color_GRB[0] = s1.getRgb();
                }
                else if (s2 != null) {
                    color_GRB[0] = s2.getRgb();
                }
                else if (s3 != null) {
                    color_GRB[0] = s3.getRgb();
                }
                else if (s4 != null) {
                    color_GRB[0] = s4.getRgb();
                }
                else if (s5 != null) {
                    color_GRB[0] = s5.getRgb();
                }
                else if (s6 != null) {
                    color_GRB[0] = s6.getRgb();
                }
            }
        });
        return color_GRB[0];
    }
    /*
    获取网络图片的Bitmap
     */
    private Bitmap getBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {

        }
        return super.onKeyDown(keyCode, event);
    }
    public void startIntentActivity(BaseActivity activity1,BaseActivity activity2){
        Intent intent = new Intent();
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2, String name, int value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2, String name1, int value1, String name2, int value2){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, value2);
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2, String name1, String value1, String name2, int value2){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, value2+"");
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2,
                                    String name1, String value1, String name2, int value2
        , String name3, int value3){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, value2);
        intent.putExtra(name3, value3);
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }


    public void startIntentActivity(BaseActivity activity1, BaseActivity activity2,
                                    String name1, String value1, String name2, int value2
            , String name3, String value3){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, ""+value2);
        intent.putExtra(name3, value3);
        intent.setClass(activity1, activity2.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(BaseActivity activity1,Fragment fragment2){
        Intent intent = new Intent();
        intent.setClass(activity1, fragment2.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(BaseActivity activity1, Fragment fragment2, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(activity1, fragment2.getClass());
        startActivity(intent);
    }
}
