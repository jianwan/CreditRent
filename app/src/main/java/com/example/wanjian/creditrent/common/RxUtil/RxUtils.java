package com.example.wanjian.creditrent.common.RxUtil;


import com.example.wanjian.creditrent.common.Result;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Soully 2017\7\28
 * Rx2.0系列的封装
 * Info: 封装的 Rx 工具
 */
public class RxUtils {


    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
//        return  upstream -> upstream.subscribeOn(Schedulers.io())
//                        .unsubscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread());
//        };
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }



    public static <T> ObservableTransformer<Result<T>, T> handleResult() {
        return upstream -> upstream.flatMap(tResult -> {
            if (tResult.code == 1) {
                return createData(tResult.data);
            } else {
                return Observable.error(new ApiException(tResult.code, tResult.msg));
            }
        });
//        return new ObservableTransformer<Result<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<Result<T>> upstream) {
//                return upstream.flatMap((Function<? super Result<T>, ? extends ObservableSource<? extends T>>) new Function<Result<T>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Result<T> tResult) throws Exception {
//                        if (tResult.code == 1) {
//                    return createData(tResult.data);
//                } else {
//                    return Observable.error(new ApiException(tResult.code, tResult.msg));
//                }
//                    }
//                });
//            }
//        };
    }

    public static <T> ObservableTransformer<Result<T>,String> handleResultToMsg() {

        return upstream -> upstream.flatMap(tResult -> {
            if (tResult.code == 1) {
//                ToastUtil.show(tResult.msg.toString());
//                PLog.d("TAG",tResult.msg.toString());
                return createData(tResult.msg);
            } else {
                return Observable.error(new ApiException(tResult.code, tResult.msg));
            }
        });
//        return new ObservableTransformer<Result<T>, String>() {
//            @Override
//            public ObservableSource<String> apply(Observable<Result<T>> upstream) {
//                return upstream.flatMap((Function<? super Result<T>, ? extends ObservableSource<? extends String>>) new Function<Result<T>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Result<T> tResult) throws Exception {
//                        if (tResult.code == 1) {
//                            return createData(tResult.msg);
//                        } else {
//                            return Observable.error(new ApiException(tResult.code, tResult.msg));
//                        }
//                    }
//                });
//            }
//        };

    }


    private static <T> Observable<T> createData(final T data){
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception a) {
                    e.onError(a);
                }
            }
        });
    }

    private static Observable<String> createData(final String data) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            /**
             * Called for each Observer that subscribes.
             *
             * @param e the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception a) {
                    e.onError(a);
                }
            }
        });
    }


}
