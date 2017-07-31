package com.winterrunner.router.interfaces;


import com.winterrunner.router.bean.RouterResponseBean;

/**
 * Created by L.K.X on 2017/5/15.
 */

public interface OnResponseListener{

    /**
     * @param routerResponseBean
     * @return 返回boolean值,若是需要继续接收回调,是否完成回调，完成则删除回调的引用
     */
    boolean onSuccess(RouterResponseBean routerResponseBean);
    void onError();
}
