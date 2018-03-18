package com.example.wanjian.creditrent.common.util;

import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xcc on 2016/1/21 0021.
 * 验证码倒数
 */
public class ObservableTimer {

    private TextView textView;
    private String TAG;
    public ObservableTimer(TextView textView, String TAG) {
        this.textView = textView;
        this.TAG = TAG;
    }
    public ObservableTimer(TextView textView) {
        this.textView = textView;
    }

    public void startTimer() {
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(60).map(aLong -> 60-aLong)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        textView.setClickable(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        textView.setText(String.format("获取验证码(%ss)", value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        PLog.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        textView.setClickable(true);
                        textView.setText("重新发送验证码");
                    }
                });
    }
}