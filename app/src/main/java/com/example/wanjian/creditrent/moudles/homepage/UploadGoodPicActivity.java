package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
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

    private PhotoAdapter photoAdapter;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> onLongClickListData = new ArrayList<>();

    private ImageView iv_crop;
    private RecyclerView recyclerView;

    //拍照
    private static final int TAKE_PICTURE = 0;
    //相册
    private static final int CHOOSE_PICTURE = 1;
    //剪裁请求码
    private static final int CROP_SMALL_PICTURE = 2;
    //调用照相机返回图片文件

    private File picFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_uploadgoods_pic);

        Bundle bundle = this.getIntent().getExtras();
        goodId = bundle.getString("goodid");
        ToastUtil.show(goodId);


        initViews();

        initRecyclerview();

    }

    private void initRecyclerview() {

        iv_crop = (ImageView) findViewById(R.id.uploadgoodsPic_iv_pic);

        recyclerView = (RecyclerView) findViewById(R.id.uploadgoodsPic_recyclerview);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MIN)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(true)
                                    .setSelected(selectedPhotos)
                                    .start(UploadGoodPicActivity.this);
                        } else {
                            //TODO:此处不可打开删除按钮，有未知bug···
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(UploadGoodPicActivity.this);
                        }
                    }
                }));

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

        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadgoodsPic_toolbar_back:
                onBackPressed();
                break;
            case R.id.uploadgoodsPic_iv_pic:
            case R.id.uploadgoodsPic_iv_tv:

                break;
            case R.id.uploadgoodsPic_button_upload:
                if (!selectedPhotos.isEmpty()){
                    zipPic(picFile);
                }else {
                    ToastUtil.show("请先选中图片再上传");
                }
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            iv_crop.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
                //上传图片
                picFile = new File(photos.get(0));
            }
            photoAdapter.notifyDataSetChanged();
        }

        //拍照功能或者裁剪后返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            iv_crop.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(Uri.fromFile(new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH)))).into(iv_crop);
            //上传图片
            picFile = new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH));
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
                String fileName = "goodsimg" + ".png";
                uploadGoodPics(compressFile, fileName);
            }else {
                String fileName = "goodsimg" + ".png";
                uploadGoodPics(oldFile, fileName);
            }
        }catch (Exception e){
            PLog.d(e.toString());
        }

    }


    private void uploadGoodPics(File file,String fileName) {

        int id = Integer.parseInt(goodId);

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
                        startIntentActivity(UploadGoodPicActivity.this,new UploadGoodsOkActivity());
                        ToastUtil.show("图片上传成功~");
                    }
                });
    }


}
