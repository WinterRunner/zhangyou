package com.threesome.function.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * author: L.K.X
 * created on: 2017/7/20 下午8:43
 * description:
 */

public class StatusBarUtils {



    //设置状态栏的颜色
    public static void setStatusBarColor(Activity activity,int color) {
        setStatusBarDisable(activity);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight(activity);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
            if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                //不预留系统空间
                ViewCompat.setFitsSystemWindows(mChildView, false);
                lp.topMargin += statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
        View statusBarView = mContentView.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
            //避免重复调用时多次添加 View,在fragment中要是多次调用此方法，会造成多次添加View
            statusBarView.setBackgroundColor(Color.BLUE);
        } else {
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setBackgroundColor(Color.BLUE);
            //向 ContentView 中添加假 View
            mContentView.addView(statusBarView, 0, lp);
        }
    }

    //此方法，可以让4.4以上，包括5.0以上，实现整个内容沉浸到statusbar的下面（若是需要沉浸一些图片效果，调用此方法即可，在布局中顶部放置要展示的图片）
    public static void setStatusBarDisable(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.0到5.0
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private  static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
