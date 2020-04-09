package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * 多个
     *
     * @param view
     */
    public void testBackgroundWork2(View view) {
        //单一的一次任务
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorkManager2.class).build();
        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MainWorkManager3.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(MainWorkManager4.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(MainWorkManager5.class).build();

        //顺序执行
       /* WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
                .then(oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .enqueue();*/
        //并发执行 旧版本  2  3  同时执行，执行完成后，再执行 4 5
       /* WorkManager.getInstance(this).beginWith(oneTimeWorkRequest, oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .enqueue();*/
        // 把Request 存入集合
        List<OneTimeWorkRequest> oneTimeWorkRequestList = new ArrayList<>();
        oneTimeWorkRequestList.add(oneTimeWorkRequest);// 测试：没有并行
        oneTimeWorkRequestList.add(oneTimeWorkRequest2);// 测试：没有并行
        oneTimeWorkRequestList.add(oneTimeWorkRequest3);// 测试：没有并行
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequestList)
                .then(oneTimeWorkRequest4)
                //该任务仍旧是串行,需要等到前面并行任务完成后才开始执行
                .enqueue();
    }

    /**
     * 重复执行后台任务
     *
     * @param view
     */
    public void testBackgroundWork3(View view) {
        //重复多次 多次、循环, 最小循环周期 15分钟
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                //即使设置了10分钟,实际周期任然为15分钟
                .Builder(MainWorkManager2.class, 10, TimeUnit.MINUTES)
                .build();
        //监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e(TAG, "onChanged: 状态：" + workInfo.getState().name());
                if (workInfo.getState().isFinished()) {
                    Log.e(TAG, "onChanged: 状态：isFinished=true ,后台任务已经完成了...");
                }
            }
        });
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    /**
     * 约束条件 约束后台任务执行
     *
     * @param view
     */
    public void testBackgroundWork4(View view) {
        // 约束条件，必须满足条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络连接
                .setRequiresCharging(true)//充电时
                .setRequiresDeviceIdle(true)//空闲时
                .build();
        /**
         * 除了上面设置的约束外，WorkManger还提供了以下的约束作为Work执行的条件：
         *  setRequiredNetworkType：网络连接设置
         *  setRequiresBatteryNotLow：是否为低电量时运行 默认false
         *  setRequiresCharging：是否要插入设备（接入电源），默认false
         *  setRequiresDeviceIdle：设备是否为空闲，默认false
         *  setRequiresStorageNotLow：设备可用存储是否不低于临界阈值
         */
        //请求对象
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorkManager2.class)
                .setConstraints(constraints)//设置约束条件
                .build();
        //监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e(TAG, "状态：" + workInfo.getState().name());
                if (workInfo.getState().isFinished()) {
                    Log.e(TAG, "状态：isFinished=true :后台任务已经完成了...");
                }
            }
        });
        //加入队列
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    public void testBackgroundWork5(View view) {
    }

    public void testBackgroundWork6(View view) {
    }

    public void testBackgroundWork7(View view) {
        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .build();

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWorkManager2.class)
                .setConstraints(myConstraints) // TODO 3 约束条件的执行
                .build();


        WorkManager.getInstance(this) // TODO 1 初始化工作源码


                .enqueue(request); // TODO 2 加入队列执行
    }
}
