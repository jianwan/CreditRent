package com.example.wanjian.creditrent.moudles.user.moudles.user_information;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanjian.creditrent.CreditRent_Application;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.common.util.Utils;
import com.example.wanjian.creditrent.moudles.user.UserBean;
import com.jaeger.library.StatusBarUtil;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wanjian on 2017/11/8.
 * 修改用户详细信息
 */

public class UserDetailInformation extends BaseActivity implements View.OnClickListener{

    CircleImageView userdetial_ci_avatar;

    ImageView user_dl_toolbar_back;

    TextView user_dl_toolbar_change,
            userdetial_tv_nickname,userdetial_tv_sex,userdetial_tv_credit,
            userdetial_tv_school,userdetial_ci_declaration;

    Button userdetial_bt_verify;

    SwipeRefreshLayout userdetial_swipe;

    String phonenumber;


    //拍照
    private static final int TAKE_PICTURE = 0;
    //相册
    private static final int CHOOSE_PICTURE = 1;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 2;
    //调用照相机返回图片文件
    private File tempFile;

    private Uri tempUri;
    private Bitmap mBitmap;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private File picFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation);


        initView();


        initData();

        getInformationFronLocal();

        initSwipeRefresh();
    }

    private void initData() {
        //从网络获取数据
        if(Utils.isNetworkConnected(CreditRent_Application.getContext())||Utils.isWifi(CreditRent_Application.getContext())){
            phonenumber = ACache.getDefault().getAsString(C.USER_NAME);
            getInformationFromInternet(phonenumber);
        }else {
            ToastUtil.show("网络连接失败");
        }
    }


    private void initSwipeRefresh() {
        userdetial_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //从网络获取数据
                if(Utils.isNetworkConnected(CreditRent_Application.getContext())||Utils.isWifi(CreditRent_Application.getContext())){
                    phonenumber = ACache.getDefault().getAsString(C.USER_NAME);
                    getInformationFromInternet(phonenumber);
                }else {
                    ToastUtil.show("网络连接失败");
                }
            }
        });

    }



    //存储有点bug
    private void getInformationFromInternet(String phonenumber) {
        RetrofitNewSingleton.getInstance()
                .getUserInformation(phonenumber)
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean value) {

//                        userdetial_tv_credit.setText(value.getCrent().toString());
//                        PLog.d("TAG1",value.getCrent().toString());
//                        PLog.d("TAG1",value.getPhone());
//
//                        userdetial_tv_school.setText(value.getArea().toString());
//                        PLog.d("TAG2",value.getArea().toString());
//                        PLog.d("TAG2",value.getPhone());
//
//                        userdetial_ci_declaration.setText(value.getQianming().toString());
//                        PLog.d("TAG3",value.getQianming().toString());
//                        PLog.d("TAG3",value.getPhone());
//
//                        userdetial_tv_sex.setText(value.getSex());
//                        PLog.d("TAG4",value.getSex().toString());
//                        PLog.d("TAG4",value.getPhone());
//
//                        userdetial_bt_verify.setText(value.getRenzheng());
//                        PLog.d("TAG5",value.getRenzheng());
//                        PLog.d("TAG5",value.getPhone());
//
//                        Glide.with(getBaseContext())
//                                .load(value.getImg())
//                                .into(userdetial_ci_avatar);

                        userdetial_tv_nickname.setText(value.getPhone().toString());
                        userdetial_tv_credit.setText(value.getCrent().toString());
                        userdetial_tv_school.setText(value.getArea().toString());
                        userdetial_tv_sex.setText(value.getSex().toString());
                        userdetial_bt_verify.setText(value.getRenzheng().toString());
                        userdetial_ci_declaration.setText(value.getQianming().toString());
                        Glide.with(getBaseContext())
                                .load(value.getImg())
                                .into(userdetial_ci_avatar);


                        userdetial_swipe.setRefreshing(false);
                        ToastUtil.show("信息已更新");

                        //TODO 存储数据时有问题，不能及时存入（和rxjava有关系？）
                        saveDate(value.getCrent().toString(),value.getImg().toString());

//缓存网络数据到本地
//                        ACache.getDefault().put(C.AVATAR,value.getImg());
//                        ACache.getDefault().put(C.SEX,value.getSex());
//                        ACache.getDefault().put(C.CREDIT,value.getCrent().toString());
//                        Log.d("TAG3",ACache.getDefault().getAsString(C.CREDIT));
//                        ACache.getDefault().put(C.SCHOOL,value.getArea());
//                        ACache.getDefault().put(C.DECLARATION,value.getQianming());
//                        ACache.getDefault().put(C.VERIFY,value.getRenzheng());

                    }


                    @Override
                    public void onError(Throwable e) {
                        RetrofitNewSingleton.disposeFailureInfo(e,getApplicationContext());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void saveDate(String credit, String img) {
        ACache.getDefault().put(C.AVATAR,img);
        SharedPreferencesUtil.setCredit(credit);
    }


    //本地获取数据
    private void getInformationFronLocal() {
        String avatar = ACache.getDefault().getAsString(C.AVATAR);
        String nickname = ACache.getDefault().getAsString(C.NICKNAME);
        String sex = ACache.getDefault().getAsString(C.SEX);
        String credit = ACache.getDefault().getAsString(C.CREDIT);
        String school = ACache.getDefault().getAsString(C.SCHOOL);
        String declaration = ACache.getDefault().getAsString(C.DECLARATION);
        String verify = ACache.getDefault().getAsString(C.VERIFY);


        Glide.with(getBaseContext())
                .load(avatar)
                .into(userdetial_ci_avatar);
        userdetial_tv_nickname.setText(nickname);
        userdetial_tv_sex.setText(sex);
        if (credit!=null&&credit.equals("")){
            userdetial_tv_credit.setText(credit);
        }
        userdetial_tv_school.setText(school);
        userdetial_ci_declaration.setText(declaration);
        if (verify!=null&&!verify.equals("")){
            userdetial_bt_verify.setText(verify);
        }else {
            userdetial_bt_verify.setText("实名认证");
        }

    }

    private void initView() {
        userdetial_ci_avatar=(CircleImageView)findViewById(R.id.userdetial_ci_avatar);

        user_dl_toolbar_back=(ImageView)findViewById(R.id.user_dl_toolbar_back);
        user_dl_toolbar_change=(TextView)findViewById(R.id.user_dl_toolbar_change);

        userdetial_tv_nickname=(TextView)findViewById(R.id.userdetial_tv_nickname);
        userdetial_tv_sex=(TextView)findViewById(R.id.userdetial_tv_sex);
        userdetial_tv_credit=(TextView)findViewById(R.id.userdetial_tv_credit);
        userdetial_tv_school=(TextView)findViewById(R.id.userdetial_tv_school);
        userdetial_ci_declaration=(TextView)findViewById(R.id.userdetial_ci_declaration);

        userdetial_bt_verify = (Button) findViewById(R.id.userdetial_bt_verify);

        userdetial_swipe = (SwipeRefreshLayout) findViewById(R.id.userdetial_swipe);

        user_dl_toolbar_back.setOnClickListener(this);
        user_dl_toolbar_change.setOnClickListener(this);
        userdetial_ci_avatar.setOnClickListener(this);
        userdetial_bt_verify.setOnClickListener(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_dl_toolbar_back:
                onBackPressed();
                break;
            case R.id.user_dl_toolbar_change:
                startIntentActivity(this,new UserDetailInformationChange());
                break;
            case R.id.userdetial_ci_avatar:

                new AlertDialog.Builder(UserDetailInformation.this).setItems(new String[]{"照相机", "相册", "取消"},
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TAKE_PICTURE:
//                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp_image.jpg"));
//                                // 将拍照所得的相片保存到SD卡根目录
//                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
//                                break;

                                PhotoPicker.builder()
                                        .setOpenCamera(true)
                                        .setCrop(true)
                                        .setCropXY(1, 1)
                                        .setCropColors(R.color.main_toolbar, R.color.main_toolbar)
                                        .start(UserDetailInformation.this);

                                break;
                            case CHOOSE_PICTURE:
//                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                                openAlbumIntent.setType("image/*");
//                                startActivityForResult(openAlbumIntent,CHOOSE_PICTURE);
//                                break;


                                PhotoPicker.builder()
                                        .setPhotoCount(1)
                                        .setPreviewEnabled(false)
                                        .setCrop(true)
                                        .setCropXY(1, 1)
                                        .setCropColors(R.color.main_toolbar, R.color.main_toolbar)
                                        .start(UserDetailInformation.this);
                                break;
                            case 2:
                                break;
                        }
                    }
                }).create().show();
                break;
            case R.id.userdetial_bt_verify:
                if (userdetial_bt_verify.getText().toString().equals("实名认证已通过")){
                    ToastUtil.show("您的实名认证已通过，请勿再次申请~");
                }else {
                    startIntentActivity(this,new UserVerifyActivity());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case TAKE_PICTURE:
//                    cutImage(tempUri); // 对图片进行裁剪处理
//                    break;
//                case CHOOSE_PICTURE:
//                    cutImage(data.getData()); // 对图片进行裁剪处理
//                    break;
//                case CROP_SMALL_PICTURE:
//                    if (data != null) {
//                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
//                    }
//                    break;
//            }
//        }


        //选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            userdetial_ci_avatar.setVisibility(View.GONE);

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
                //上传图片
                picFile = new File(photos.get(0));
                zipPic(picFile);
            }

        }
        //拍照功能或者裁剪后返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            userdetial_ci_avatar.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(Uri.fromFile(new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH)))).into(userdetial_ci_avatar);
            //上传图片
            picFile = new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH));
            zipPic(picFile);
        }

    }

    //压缩图片并展示
    private void zipPic(File oldFile) {
        try{
            if (oldFile.length() >= 1048576) {
                File compressFile = new CompressHelper.Builder(getBaseContext())
                        .setMaxWidth(720)
                        .setMaxHeight(960)
                        .build()
                        .compressToFile(oldFile);
                String fileName = "avatar" + ".png";
                uploadAvater(compressFile,fileName);
            }else {
                String fileName = "avatar" + ".png";
                uploadAvater(oldFile, fileName);
            }
        }catch (Exception e){
            PLog.d(e.toString());
        }

    }

    private void uploadAvater(File file, String fileName) {

        RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfile",fileName,requestFile);

        RetrofitNewSingleton.getInstance()
                .uploadImgFile(body)
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
                        ToastUtil.show("头像上传成功");
                    }
                });

    }




//    //之前上传头像的方法，因Android 7.0权限问题改用开源库 photopicker
//
//    /**
//     * 裁剪图片方法实现
//     */
//    protected void cutImage(Uri uri) {
//        if (uri == null) {
//            Log.i("dyp", "The uri is not exist.");
//        }
//        tempUri = uri;
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        //com.android.camera.action.CROP这个action是用来裁剪图片用的
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_SMALL_PICTURE);
//    }
//    /**
//     * 保存裁剪之后的图片数据
//     */
//    protected void setImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            mBitmap = extras.getParcelable("data");
//            userdetial_ci_avatar.setImageBitmap(mBitmap);//头像设置为新的图片
//            uploadPic(mBitmap);//上传图片到服务器
//        }
//    }
//
//    private String uploadPic(Bitmap bitmap) {
//
//        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = "avatar" + ".png";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.flush();
//            fos.close();
//
//
//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//            MultipartBody.Part body = MultipartBody.Part.createFormData("myfile",fileName,requestFile);
//
//            RetrofitNewSingleton.getInstance()
//                    .uploadImgFile(body)
//                    .subscribe(new Observer<String>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(String value) {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            RetrofitNewSingleton.disposeFailureInfo(e,getApplicationContext());
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            ToastUtil.show("上传成功");
//                        }
//                    });
//
//
//            return file.getAbsolutePath();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//    }


}
