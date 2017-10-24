package com.example.wanjian.creditrent.common.RxUtil;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    // 主题
    private final Subject<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        bus = PublishSubject.create().toSerialized();
        //bus = new SerializedSubject<>(PublishSubject.create());
        //  bus = new SerializedS
    }

    // 静态内部类(单例模式的内部类实现方法)
    private static class SingletonHolder {
        public final static RxBus sInstance = new RxBus();
    }

    // 单例RxBus
    public static synchronized  RxBus getDefault() {
        return SingletonHolder.sInstance;
    }


    /**
     * 提供了一个新的事件
     * 发布
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }



    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);       //判断接收事件类型
    }
}
