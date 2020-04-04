package com.example.jetpackdemo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jetpackdemo.databus.LiveDataBus;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ObserverTest mObserverTest;
    private TextView mTextView;
    private NameViewModel mNameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mObserverTest = new ObserverTest();
//        getLifecycle().addObserver(mObserverTest);
        mTextView = findViewById(R.id.text);
        mNameViewModel = ViewModelProviders.of(this).get(NameViewModel.class);
        //需要一个观察者来观察数据
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.e(TAG, "onChanged: 数据发生变化");
                mTextView.setText(mNameViewModel.getCurrentName().getValue() + "");
            }
        };
        //订阅
        mNameViewModel.getCurrentName().observe(this, observer);
    }

    int i = 0;

    public void changeText(View view) {
        mNameViewModel.getCurrentName().setValue("Jhao" + i++);
    }

    public void testLiveDataBus(View view) {
        startActivity(new Intent(this, TestLiveDataBusActivity.class));
        //发送消息
        LiveDataBus.getInstance().with("data", String.class).setValue("jhao");
    }
}
