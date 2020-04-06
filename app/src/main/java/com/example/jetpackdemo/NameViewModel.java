package com.example.jetpackdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public int i = 0;

//    public int getI() {
//        return i;
//    }
//
//    public void setI(int i) {
//        this.i = i;
//    }
}
