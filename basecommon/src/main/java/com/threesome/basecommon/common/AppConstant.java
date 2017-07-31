package com.threesome.basecommon.common;

import android.os.Environment;

import java.io.File;

/**
 * author: L.K.X
 * created on: 2017/7/15 下午5:56
 * description:
 */

public class AppConstant {
    public static boolean IS_DEBUG =true;
    public static String DEBUG_TAG  = "System.out";
    public static final String SHARE_PREFERENCE_NAME = "zhangyou";
    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"zhangyou"+File.separator;
    public static final String BASE_URL = "http://api.jisuduobao.com/yydb/";
}
