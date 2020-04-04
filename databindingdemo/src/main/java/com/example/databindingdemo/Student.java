package com.example.databindingdemo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

/**
 * @author JiangHao
 * @date 2020/4/5
 * @describe
 */
public class Student extends BaseObservable {

    private String id;
    private String name;


    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
