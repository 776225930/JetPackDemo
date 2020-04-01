package com.example.jetpackdemo;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jetpackdemo.databus.LiveDataBus;

public class TestLiveDataBusActivity extends AppCompatActivity {

    private static final String TAG = TestLiveDataBusActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_live_data_bus);
        //接收消息
        LiveDataBus.getInstance().with("data", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.e(TAG, "onChanged: " + s);
                Toast.makeText(TestLiveDataBusActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
