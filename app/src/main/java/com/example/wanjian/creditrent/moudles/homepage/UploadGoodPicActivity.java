package com.example.wanjian.creditrent.moudles.homepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by 17631 on 2018/5/29.
 */

public class UploadGoodPicActivity extends BaseActivity implements View.OnClickListener{

    ImageView back,pic;
    TextView tv;
    Button uploadPic;
    String goodId;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_uploadgoods_pic);

        Bundle bundle = this.getIntent().getExtras();
        goodId = bundle.getString("goodid");
        ToastUtil.show(goodId);


        initViews();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.uploadgoodsPic_toolbar_back);
        pic = (ImageView) findViewById(R.id.uploadgoodsPic_iv_pic);
        tv = (TextView) findViewById(R.id.uploadgoodsPic_iv_tv);
        uploadPic = (Button) findViewById(R.id.uploadgoodsPic_button_upload);

        back.setOnClickListener(this);
        pic.setOnClickListener(this);
        tv.setOnClickListener(this);
        uploadPic.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadgoodsPic_toolbar_back:
                onBackPressed();
                break;
            case R.id.uploadgoodsPic_iv_pic:
            case R.id.uploadgoodsPic_iv_tv:
                new AlertDialog.Builder(UploadGoodPicActivity.this).setItems(new String[]{"照相机", "相册", "取消"}, new DialogInterface.OnClickListener() {
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
                break;
            case R.id.uploadgoodsPic_button_upload:
                startIntentActivity(this,new UploadGoodsOkActivity());
                ToastUtil.show("上传成功");
                break;
        }
    }

    private void uploadGoodPic(Bitmap mbitmap) {


        int id = Integer.parseInt(goodId);

        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = "goodsimg" + ".png";
        file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("goodsimg",fileName,requestFile);

            RetrofitNewSingleton.getInstance()
                    .uploadGoodspic(id,1,body)
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String value) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                        }

                        @Override
                        public void onComplete() {
                            ToastUtil.show("图片上传成功~点击即可继续上传");
                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }

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
            pic.setImageBitmap(mBitmap);//头像设置为新的图片
            tv.setText("点击再次上传");
            uploadGoodPic(mBitmap);//上传图片到服务器
        }
    }


}
