//package com.example.wanjian.creditrent.common.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//
//import com.example.wanjian.creditrent.BuildConfig;
//import com.example.wanjian.creditrent.CreditRent_Application;
//import com.example.wanjian.creditrent.base.C;
//import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
//import com.example.wanjian.creditrent.common.CreditRent_Context;
//import com.example.wanjian.creditrent.common.RxUtil.RxUtils;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import io.reactivex.subjects.Subject;
//
///**
// * Created by myValentine on 16/8/3.
// */
//
//public class UploadPicture {
//
//    public static String cameraPath = CreditRent_Application.cacheDir + "/Data/cacheImage";
//    public static String cropPath = CreditRent_Application.cacheDir + "/Data/cropImage";
//    public static Uri cameraUri = Uri.fromFile(new File(cameraPath));
//    public static Uri cropUri = Uri.fromFile(new File(cropPath));
//    public static byte[] imageNoCrop;
//    public static String imageUrl;
//    public static String token;
//
//    public static Intent getUploadIntent(int uploadType) {
//        switch (uploadType) {
//            case C.PICK_FROM_FILE:
//                Intent intentFile = new Intent(Intent.ACTION_PICK);
//                intentFile.setType("image/*");
//                return intentFile;
//            case C.PICK_FROM_CAMERA:
//                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
//                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//        }else {
//                    cameraUri= FileProvider.getUriForFile(CreditRent_Context.getApplicationContext(),
//                            BuildConfig.APPLICATION_ID + ".fileprovider",new File(cameraPath));
//                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//                    intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intentCamera.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                }
//                return intentCamera;
//            default:
//                return null;
//        }
//    }
//
//    public static Intent cropPicture(Uri uri, int outputX, int outputY) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        }
//        //这里是否会出现4.4分水岭导致的Uri问题，未验证
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//        return intent;
//    }
//
//    public static Intent cropPicture(int outputX, int outputY) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(cameraUri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//        return intent;
//    }
//
//
//
//    public static void uploadPicture(final UpCompletionHandler completionHandler) {
//        final String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
//        imageUrl = "http://olrkfzprs.bkt.clouddn.com/" + fileName;
//        final Up
//        final UploadManager uploadManager = new UploadManager();
//        RetrofitNewSingleton.getApiService().getToken()
//                .filter(stringResult -> stringResult.code==1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(stringResult -> stringResult.data)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String value) {
//                        UploadPicture.token = value;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        uploadManager.put(cropPath, fileName, UploadPicture.token, completionHandler, null);
//                    }
//                });
//    }
//
//    public static void uploadPictureNoCrop(Uri uri, Context context, final UpCompletionHandler completionHandler) {
//        Bitmap bitmap = BitmapUtils.compressImage(uri, context);
//        imageNoCrop = BitmapUtils.Bitmap2Bytes(bitmap);
//        final String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
//        imageUrl = "http://o7xx27udo.bkt.clouddn.com/" + fileName;
//        final UploadManager uploadManager = new UploadManager();
//        RetrofitNewSingleton.getInstance().getToken()
//                .subscribe(new Subject<String>() {
//                               @Override
//                               public boolean hasObservers() {
//                                   return false;
//                               }
//
//                               @Override
//                               public boolean hasThrowable() {
//                                   return false;
//                               }
//
//                               @Override
//                               public boolean hasComplete() {
//                                   return false;
//                               }
//
//                               @Override
//                               public Throwable getThrowable() {
//                                   return null;
//                               }
//
//                               @Override
//                               protected void subscribeActual(Observer<? super String> observer) {
//
//                               }
//
//                               @Override
//                               public void onSubscribe(Disposable d) {
//
//                               }
//
//                               @Override
//                               public void onNext(String value) {
//                                   UploadPicture.token = value;
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//
//                               }
//
//                               @Override
//                               public void onComplete() {
//                                   uploadManager.put(imageNoCrop, fileName, UploadPicture.token, completionHandler, null);
//                               }
//                           }
//                );
//    }
//
//
//    public static void uploadEditPicture(Uri uri, Context context, UpCompletionHandler completionHandler) {
//        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS").format(new Date());
//        imageUrl = "http://o7xx27udo.bkt.clouddn.com/" + fileName;
//        if (uri != null) {
//            Bitmap bitmap = BitmapUtils.compressImage(uri, context);
//
//            UploadManager uploadManager = new UploadManager();
//            RetrofitNewSingleton.getApiService().getToken()
//                    .compose(RxUtils.rxSchedulerHelper())
//                    .subscribe(new Subject<Result<String>>() {
//                                   @Override
//                                   public boolean hasObservers() {
//                                       return false;
//                                   }
//
//                                   @Override
//                                   public boolean hasThrowable() {
//                                       return false;
//                                   }
//
//                                   @Override
//                                   public boolean hasComplete() {
//                                       return false;
//                                   }
//
//                                   @Override
//                                   public Throwable getThrowable() {
//                                       return null;
//                                   }
//
//                                   @Override
//                                   protected void subscribeActual(Observer<? super Result<String>> observer) {
//
//                                   }
//
//                                   @Override
//                                   public void onSubscribe(Disposable d) {
//
//                                   }
//
//                                   @Override
//                                   public void onNext(Result<String> value) {
//                                       UploadPicture.token = value.data;
//                                   }
//
//                                   @Override
//                                   public void onError(Throwable e) {
//                                        ToastUtil.show("获取Token失败");
//                                   }
//
//                                   @Override
//                                   public void onComplete() {
//                                       uploadManager.put(BitmapUtils.getBytes(bitmap), fileName, UploadPicture.token, completionHandler, null);
//                                   }
//                               }
//                    );
//        }
//    }
//
//
//    public static void showPictureSelectDialog(Context context, Activity activity) {
//        new AlertDialog.Builder(context).setItems(new String[]{"相册", "照相机", "取消"}, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:
//                        activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_FILE), C.PICK_FROM_FILE);
//                        break;
//                    case 1:
//                        activity.startActivityForResult(UploadPicture.getUploadIntent(C.PICK_FROM_CAMERA), C.PICK_FROM_CAMERA);
//                        break;
//                    case 2:
//                        dialog.dismiss();
//                        break;
//                }
//            }
//        }).create().show();
//    }
//
//
//    private static String SAVE_PIC_PATH = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
//            Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡;
//
//    private String saveBitmapToSDCard(Bitmap bitmap, String imagename) {
//        FileOutputStream fos = null;
//        try {
//            String filePath = SAVE_PIC_PATH + "/campussay/images/img-" + imagename + ".png";
//            File file = new File(filePath);
//            if (!file.exists()) {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            }
//            fos = new FileOutputStream(file);
//            if (fos != null) {
//                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
//                fos.close();
//                return filePath;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return null;
//    }
//
//}
