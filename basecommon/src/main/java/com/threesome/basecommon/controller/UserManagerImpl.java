package com.threesome.basecommon.controller;

import android.text.TextUtils;

import com.threesome.basecommon.BaseApplication;
import com.threesome.function.controller.UserManager;
import com.threesome.function.utils.SPUtils;
import com.threesome.function.utils.encrypt.DES3;

/**
 * author: L.K.X
 * created on: 2017/7/29 下午4:01
 * description:
 */

public class UserManagerImpl implements UserManager {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String COOKIE = "cookie";

    @Override
    public boolean isLogin() {
        return !(TextUtils.isEmpty(SPUtils.getString(BaseApplication.instance, USERNAME, "")) || TextUtils.isEmpty(SPUtils.getString(BaseApplication.instance, PASSWORD, "")));
    }

    @Override
    public String getUsername() {
        return DES3.decode(SPUtils.getString(BaseApplication.instance, USERNAME, ""), AppManagerImpl.getInstance().getRequestkey());
    }

    @Override
    public String getPassword() {
        return DES3.decode(SPUtils.getString(BaseApplication.instance, PASSWORD, ""), AppManagerImpl.getInstance().getRequestkey());
    }

    @Override
    public void login(String username, String password) {
        SPUtils.putString(BaseApplication.instance, USERNAME, DES3.encode(username, AppManagerImpl.getInstance().getRequestkey()));
        SPUtils.putString(BaseApplication.instance, PASSWORD, DES3.encode(password, AppManagerImpl.getInstance().getRequestkey()));
    }

    @Override
    public void logout() {
        SPUtils.putString(BaseApplication.instance, USERNAME, "");
        SPUtils.putString(BaseApplication.instance, PASSWORD, "");
    }

    private String cookie;
    @Override
    public void setCookie(String cookie) {
        this.cookie = cookie;
        SPUtils.putString(BaseApplication.instance, COOKIE, cookie);
    }
    @Override
    public String getCookie() {
        if (cookie == null) {
            cookie = SPUtils.getString(BaseApplication.instance, COOKIE, "");
        }
        return cookie;
    }
}
