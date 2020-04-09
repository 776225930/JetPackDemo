package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * @author Jianghao
 * @date 2020/4/9 23:06
 * 场景：非及时任务 （例如：每天同步数据，每天上传一次日志）
 * Room数据库管理（持久性）
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 测试后台任务
     *
     * @param view
     */
    public void testBackgroundWork1(View view) {
        //单一的任务，只执行一次
        OneTimeWorkRequest oneTimeWorkRequest;
        //要发送的数据
        Data sendData = new Data.Builder().putString("data_send", "hello !").build();
        //请求对象初始化
        oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(MainWorkManager.class)
                .setInputData(sendData)
                .build();

        //监听数据返回
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.e(TAG, "onChanged: 状态： " + workInfo.getState().name());
                        Log.e(TAG, "onChanged: received the back data : " + workInfo.getOutputData().getString("data_back"));
                        if (workInfo.getState().isFinished()) {
                            Log.e(TAG, "状态：isFinished=true ：后台任务已经完成了...");
                        }

                    }
                });
        //请求对象 加入到队列
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    public void testBackgroundWork2(View view) {
    }

    public void testBackgroundWork3(View view) {
    }

    public void testBackgroundWork4(View view) {
    }

    public void testBackgroundWork5(View view) {
    }

    public void testBackgroundWork6(View view) {
    }

    public void testBackgroundWork7(View view) {
    }
}
