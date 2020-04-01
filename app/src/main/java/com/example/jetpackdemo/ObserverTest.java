package com.example.jetpackdemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * @author JiangHao
 * @date 2020/3/31
 * @describe
 */
public class ObserverTest implements LifecycleObserver {

    private static final String TAG = ObserverTest.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        Log.e(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestory() {
        Log.e(TAG, "onDestory: ");
    }
}
