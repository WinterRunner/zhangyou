package com.threesome.module_login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.threesome.basecommon.base.BaseFragment;
import com.threesome.module_login.login.LoginFragment;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午5:16
 * description:
 */

public class LoginContainerActivity extends AppCompatActivity {
    public static final String TYPE ="type";
    public static final int TYPE_LOGIN = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_container_lg);
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        BaseFragment baseFragment = null;

        switch (getIntent().getIntExtra(TYPE,TYPE_LOGIN)){
            case TYPE_LOGIN:
                baseFragment = new LoginFragment();
                break;
        }
        supportFragmentManager.beginTransaction().replace(R.id.fl_container_lg,baseFragment).commit();
    }
}
