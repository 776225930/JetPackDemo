package com.example.jetpackdemo.databus;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiangHao
 * @date 2020/4/7
 * @describe 使用Hook解决LiveData 第一次传递的消息可能已经过时
 */
public class LiveDataBusX {
    private static final String TAG = LiveDataBusX.class.getSimpleName();
    //存放订阅者
    private static Map<String, BusMutableLiveData<Object>> bus;
    private static LiveDataBusX instance = new LiveDataBusX();

    private LiveDataBusX() {
        bus = new HashMap<>();
    }

    public static LiveDataBusX getInstance() {
        return instance;
    }

    /**
     * 注册订阅者
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }

    /**
     * 替换掉LiveData中的SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers
     * 通过修改 ObserverWrapper observer的mLastVersion和 LiveData.mVersion相等来屏蔽掉第一次数据分发不及时
     *
     * @param <T>
     */
    public class BusMutableLiveData<T> extends MutableLiveData<T> {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            hook(observer);
        }

        private void hook(Observer<? super T> observer) {

            try {
                //1.得到mLastVersion
                Class<LiveData> liveDataClass = LiveData.class;
                //先得到mObservers
                Field mObserversField = liveDataClass.getDeclaredField("mObservers");
                mObserversField.setAccessible(true);
                //获取到这个成员变量mObservers的对象
                Object mObserversObject = mObserversField.get(this);//liveData持有该对象所以传入this
                //获取mObservers中的mLastVersion:
                //得到map对应的class对象
                Class<?> mObserversClass = mObserversObject.getClass();
                //需要执行get方法
                Method get = mObserversClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                //根据这一句androidx.arch.core.internal.SafeIterableMap.putIfAbsent(第65行)
                //androidx.lifecycle.LiveData.observe 170行
                Object invokeEntry = get.invoke(mObserversObject, observer);

                Object observerWraper = null;
                if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                    observerWraper = ((Map.Entry) invokeEntry).getValue();
                }
                if (invokeEntry == null) {
                    throw new NullPointerException("observerWraper is null!");
                }
                //得到ObserverWrapper的类对象,注意编译擦除问题
                Class<?> observerWraperClass = observerWraper.getClass().getSuperclass();
                Class<?> observerClass = observerWraper.getClass();
                Log.e(TAG, "hook: getSuperclass "+observerWraperClass );
                Log.e(TAG, "hook: getClass "+observerClass );
                Field mLastVersionField = observerWraperClass.getDeclaredField("mLastVersion");
                mLastVersionField.setAccessible(true);
                //2.得到mVersion
                Field mLastVersion = liveDataClass.getDeclaredField("mVersion");
                mLastVersion.setAccessible(true);
                //3.mLastVersion填到mVersion中
                Object mVersion = mLastVersion.get(this);
                mLastVersionField.set(observerWraper, mVersion);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
