package com.threesome.module_login.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.threesome.module_login.LoginContainerActivity;
import com.winterrunner.router.action.Action;
import com.winterrunner.router.bean.RouterRequestBean;
import com.winterrunner.router.bean.RouterResponseBean;
import com.winterrunner.router.interfaces.OnResponseListener;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午6:01
 * description:
 */

public class OpenLoginAction extends Action{
    @Override
    public RouterResponseBean invoke(Context context, RouterRequestBean routerRequest) {
        Intent intent = new Intent(context, LoginContainerActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(LoginContainerActivity.TYPE,LoginContainerActivity.TYPE_LOGIN);
        context.startActivity(intent);
        return null;
    }

    @Override
    public void invoke(Context context, RouterRequestBean routerRequest, OnResponseListener onResponseListener) {

    }
}
