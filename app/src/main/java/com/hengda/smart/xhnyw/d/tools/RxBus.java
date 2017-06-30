package com.hengda.smart.xhnyw.d.tools;
import com.hengda.smart.xhnyw.d.model.RxBusBaseMessage;

import rx.subjects.SerializedSubject;

public class RxBus {

    private static volatile RxBus mInstance;
    private final rx.subjects.Subject<Object,Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(rx.subjects.PublishSubject.create());

    }

    /**
     * 单例RxBus
     *
     * @return
     */
    public static RxBus getDefault() {
        RxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }
    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o,int num) {
        bus.onNext(o);
    }
    /**
     * 提供了一个新的事件,根据code进行分发
     * @param code 事件code
     * @param o
     */
    public void post(int code, Object o){
        bus.onNext(new RxBusBaseMessage(code,o));

    }    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> rx.Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 返回被观察者
     *
     * @return
     */
    public rx.Observer<Object> toObservable() {
        return bus;
    }

}
