package com.threesome.basecommon;

import android.app.Application;

import com.threesome.basecommon.controller.AppManagerImpl;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午5:44
 * description:
 */

public class BaseApplication extends Application{
    public static BaseApplication instance;
    private AppManagerImpl appManager = new AppManagerImpl();//帮助父类实现单例
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appManager.init(this);
    }
}
