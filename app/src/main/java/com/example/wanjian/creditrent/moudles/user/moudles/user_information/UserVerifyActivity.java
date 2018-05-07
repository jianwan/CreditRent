package com.example.wanjian.creditrent.moudles.user.moudles.user_information;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wanjian on 2018/4/7.
 */

public class UserVerifyActivity extends BaseActivity implements View.OnClickListener{

    private EditText user_verify_truename,user_verify_phone,user_verify_studentId,user_verify_code;
    private ImageView user_dl_verify_toolbar_back,user_verify_pic;
    private Button user_verify_submit;

    String truename,phone,studentId,verifyCode;

    //拍照
    private static final int TAKE_PICTURE = 0;
    //相册
    private static final int CHOOSE_PICTURE = 1;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 2;
    //调用照相机返回图片文件
    String fileName;
    private File file;

    private Uri tempUri;
    private Bitmap mBitmap;

    MaterialDialog dialog;

    Boolean isUploadPic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation_verify);

        initViews();

    }

    private void initViews() {

        user_verify_truename = (EditText) findViewById(R.id.user_verify_truename);
        user_verify_phone = (EditText) findViewById(R.id.user_verify_phone);
        user_verify_studentId = (EditText) findViewById(R.id.user_verify_studentId);
        user_verify_code = (EditText) findViewById(R.id.user_verify_code);
        user_dl_verify_toolbar_back = (ImageView) findViewById(R.id.user_dl_verify_toolbar_back);
        user_verify_pic = (ImageView) findViewById(R.id.user_verify_pic);
        user_verify_submit = (Button) findViewById(R.id.user_verify_submit);


        user_dl_verify_toolbar_back.setOnClickListener(this);
        user_verify_pic.setOnClickListener(this);
        user_verify_submit.setOnClickListener(this);
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        truename = user_verify_truename.getText().toString();
        phone = user_verify_phone.getText().toString();
        studentId = user_verify_studentId.getText().toString();
        verifyCode = user_verify_code.getText().toString();

        switch (v.getId()){
            case R.id.user_dl_verify_toolbar_back:
                onBackPressed();
                break;
            case R.id.user_verify_pic:

                if (!truename.isEmpty()&&!phone.isEmpty()&&!studentId.isEmpty()&&!verifyCode.isEmpty()){
                    new AlertDialog.Builder(UserVerifyActivity.this).setItems(new String[]{"照相机", "相册", "取消"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case TAKE_PICTURE:
                                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp_image.jpg"));
                                    // 将拍照所得的相片保存到SD卡根目录
                                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                    break;
                                case CHOOSE_PICTURE:
                                    Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                    openAlbumIntent.setType("image/*");
                                    startActivityForResult(openAlbumIntent,CHOOSE_PICTURE);
                                    break;
                                case 2:
                                    break;
                            }
                        }
                    }).create().show();
                }else {
                    ToastUtil.show("请先填写好信息再上传图片");
                }
                break;
            case R.id.user_verify_submit:

                if (!truename.isEmpty()&&!phone.isEmpty()&&!studentId.isEmpty()&&!verifyCode.isEmpty()){
                    if (isUploadPic){
                        verifyInformation(truename,phone,studentId,verifyCode);
                    }else {
                        ToastUtil.show("请先选择好图片再上传信息");
                    }
                }else {
                    ToastUtil.show("请填好所有信息再提交验证");
                }
                break;
            default:
                break;
        }
    }


    //提交验证
    private void verifyInformation(String truename,String phone,String studentId,String verifyCode) {
        RetrofitNewSingleton.getInstance()
                .verify(truename,phone,studentId,verifyCode)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        //dialog
                        dialog = new MaterialDialog.Builder(UserVerifyActivity.this)
                                .content("正在提交信息")
                                .progress(true, 0)
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                    }

                    @Override
                    public void onComplete() {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();

                                Intent intent=new Intent(UserVerifyActivity.this, UserVerificationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                ToastUtil.show("信息提交成功");
                            }
                        }, 1500);

                    }
                });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }

    }


    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("dyp", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            user_verify_pic.setImageBitmap(mBitmap);//头像设置为新的图片
            uploadPic(mBitmap);//上传图片到服务器
            isUploadPic = true;
        }
    }

    private String uploadPic(Bitmap bitmap) {

        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = "yikatong" + ".png";
        file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("myfile",fileName,requestFile);

            RetrofitNewSingleton.getInstance()
                    .verifyPic(body)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String value) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            RetrofitNewSingleton.disposeFailureInfo(e,getApplicationContext());
                        }

                        @Override
                        public void onComplete() {
                            ToastUtil.show("照片上传成功");
                        }
                    });

            return file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
