package com.example.jetpackdemo;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
