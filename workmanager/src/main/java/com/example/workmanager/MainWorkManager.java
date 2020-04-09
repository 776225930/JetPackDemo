package com.example.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
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
public class MainWorkManager extends Worker {

    private static final String TAG = MainWorkManager.class.getSimpleName();
    private Context mContext;
    private WorkerParameters mWorkerParameters;

    public MainWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams ) {
        super(context, workerParams);
        mContext = context;
        mWorkerParameters = workerParams;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: 后台任务开始执行");
        //接收Acticity传来的数据
        String dataInput = mWorkerParameters.getInputData().getString("data_send");
        Log.e(TAG, "doWork: dataInput ==" + dataInput);
        //反馈数据给Activity
        // 把任务中的数据回传到activity中
        Data data = new Data.Builder().putString("data_back", " Hi!").build();
        Result.Success success = new Result.Success(data);
        // return new Result.Failure(); // 本地执行 doWork 任务时 失败
        // return new Result.Retry(); // 本地执行 doWork 任务时 重试
        // return new Result.Success(); // 本地执行 doWork 任务时 成功 执行任务完毕
        return success;
    }
}
