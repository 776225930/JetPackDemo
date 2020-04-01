package com.example.jetpackdemo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * @author JiangHao
 * @date 2020/4/1
 * @describe
 */
public class NameViewModel extends ViewModel {
    //用来传递String类型数据
    private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

}
