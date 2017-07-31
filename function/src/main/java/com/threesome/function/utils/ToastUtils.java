package com.threesome.function.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * author: L.K.X
 * created on: 2017/7/14 下午6:09
 * description:
 */
public class ToastUtils {
    private static Context mContext;
    private static Toast toast;

    public static void init(Context context){
        mContext = context;
    }

    /**
     * 可以连续弹，不用等上一个显示
     * @param text
     */
    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);//将文本设置为toast
        toast.show();
    }
}
