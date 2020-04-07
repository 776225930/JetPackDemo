package com.example.jetpackdemo;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jetpackdemo.databus.LiveDataBus;
import com.example.jetpackdemo.databus.LiveDataBusX;

public class TestLiveDataBusActivity extends AppCompatActivity {

    private static final String TAG = TestLiveDataBusActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_live_data_bus);
        Log.e(TAG, "订阅了 data ");
        //接收消息
//        LiveDataBus.getInstance().with("data", String.class).observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                Log.e(TAG, "data target" + s);
//                Toast.makeText(TestLiveDataBusActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        });
        LiveDataBusX.getInstance().with("data", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.e(TAG, "data target" + s);
                Toast.makeText(TestLiveDataBusActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
