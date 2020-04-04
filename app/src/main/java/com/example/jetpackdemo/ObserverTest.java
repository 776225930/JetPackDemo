package com.example.jetpackdemo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
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
