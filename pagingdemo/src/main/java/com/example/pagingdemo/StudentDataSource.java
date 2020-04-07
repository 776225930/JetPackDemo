package com.example.pagingdemo;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JiangHao
 * @date 2020/4/8
 * @describe 数据源, 获取数据
 * PositionalDataSource:适用于目标数据综述固定，通过特定的位置加载数据(0-10)
 */
public class StudentDataSource extends PositionalDataSource<Student> {
    /**
     * 加载初始化数据、加载第一页数据的时候
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Student> callback) {
        //设置数据源
        callback.onResult(getStudent(0, Flag.SIZE));
    }

    /**
     * 有了初始化数据后，滑动的时候需要加载数据的话，调用该方法
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Student> callback) {

    }

    /**
     * 模拟数据源
     *
     * @param startPosition
     * @param pageSize
     * @return
     */
    private List<Student> getStudent(int startPosition, int pageSize) {
        List<Student> list = new ArrayList<Student>();
        for (int i = startPosition; i < startPosition + pageSize; i++) {
            Student student = new Student();
            student.setId("ID是: " + i);
            student.setName("名字是: Tom" + i);
            student.setGander("性别是: boy" + i);
        }
        return list;
    }

}
