package com.threesome.function.controller;

import android.content.Context;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午5:39
 * description:
 */

public abstract class AppManager{

    private static AppManager instance;
    protected AppManager() {
        instance = this;
    }
    public static AppManager getInstance() {
        return instance;
    }



    //加密
    static {
        System.loadLibrary("encrypt");
    }
    //账号密码的key
    public native String getRequestkey();
    //请求网络sign的key
    public native String getRequestkey2();




    //配置
    private UserManager userManager;
    private SettingManager settingManager;
    private DBManager dbManager;
    public  void init(Context context){
        userManager = initUserManager();
        settingManager = initSettingManager();
        dbManager = initDBManager();
    }
    public UserManager getUserManager() {
        return userManager;
    }
    public SettingManager getSettingManager() {
        return settingManager;
    }
    public DBManager getDBManager() {
        return dbManager;
    }
    protected abstract DBManager initDBManager();
    protected abstract SettingManager initSettingManager();
    protected abstract UserManager initUserManager();

}
