package com.threesome.function.controller;

/**
 * author: L.K.X
 * created on: 2017/7/29 下午3:59
 * description:
 */

public interface UserManager {
    boolean isLogin();//是否登录

    String getUsername();

    String getPassword();

    void login(String username, String password);

    void logout();

    void setCookie(String cookie);
    String getCookie();
}
