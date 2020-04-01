package com.example.jetpackdemo.databus;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JiangHao
 * @date 2020/4/1
 * @describe
 */
public class LiveDataBus {
    //存放订阅者
    private static Map<String, MutableLiveData<Object>> bus;
    private static LiveDataBus instance = new LiveDataBus();

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
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
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>) bus.get(key);
    }

}
