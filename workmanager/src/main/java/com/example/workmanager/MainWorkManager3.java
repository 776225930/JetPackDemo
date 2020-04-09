package com.example.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author JiangHao
 * @date 2020/4/9
 * @describe 后台任务
 */
public class MainWorkManager3 extends Worker {

    private static final String TAG = MainWorkManager3.class.getSimpleName();
    private Context mContext;
    private WorkerParameters mWorkerParameters;

    public MainWorkManager3(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
        mWorkerParameters = workerParams;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: 后台任务开始执行...");
        SystemClock.sleep(5000);
        Log.e(TAG, "doWork: I am done" );
        return new Result.Success();
    }
}
