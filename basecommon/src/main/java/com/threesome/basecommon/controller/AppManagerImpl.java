package com.threesome.basecommon.controller;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.threesome.basecommon.common.AppConstant;
import com.threesome.basecommon.net.NetRequestImpl;
import com.threesome.function.controller.AppManager;
import com.threesome.function.controller.DBManager;
import com.threesome.function.controller.SettingManager;
import com.threesome.function.controller.UserManager;
import com.threesome.function.utils.SPUtils;
import com.threesome.function.utils.Sout;
import com.threesome.function.utils.ToastUtils;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午5:39
 * description:
 */

public class AppManagerImpl extends AppManager{

    private NetRequestImpl netRequest;

    @Override
    public void init(Context context) {
        super.init(context);
        initLogger(AppConstant.DEBUG_TAG,AppConstant.IS_DEBUG);
        Sout.init(AppConstant.DEBUG_TAG, AppConstant.IS_DEBUG);
        SPUtils.init(AppConstant.SHARE_PREFERENCE_NAME);
        ToastUtils.init(context);
        netRequest = new NetRequestImpl();//网络类初始化
    }


    @Override
    protected DBManager initDBManager() {
        return null;
    }

    @Override
    protected SettingManager initSettingManager() {
        return null;
    }

    @Override
    protected UserManager initUserManager() {
        return new UserManagerImpl();
    }


    private void initLogger(String tag,boolean isDebug) {
        if(isDebug){
            //可以不设置
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(0)         // (Optional) How many method line to show. Default 2
                    .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                    .tag(tag)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        }
    }
}
