package com.example.databindingdemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author JiangHao
 * @date 2020/4/5
 * @describe presenter展示层
 */
public class MainViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private String username;
    private String pwd;
    private Context mContext;

    public MainViewModel(Context context) {
        mContext = context;
    }

    public TextWatcher userNameChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: username : " + charSequence);
                username = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: password : " + charSequence);
                pwd = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public void login(View view) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            if (username.equals("zhangsan") && pwd.equals("123456")) {
                Toast.makeText(mContext, "登陆成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "登陆失败!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}

