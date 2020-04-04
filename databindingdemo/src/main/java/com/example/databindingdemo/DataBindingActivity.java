package com.example.databindingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.databindingdemo.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {
    private static final String TAG = DataBindingActivity.class.getSimpleName();
    private ActivityDataBindingBinding mBinding;
    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_binding);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        mStudent = new Student("14", "Tom");
        mBinding.setStudent(mStudent);


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SystemClock.sleep(2000);
                    mStudent.setName(mStudent.getName() + i);
                    mBinding.setVariable(BR.student, mStudent);
                    Log.e(TAG, "run: " + mStudent);
                }
            }
        }).start();
    }
}
