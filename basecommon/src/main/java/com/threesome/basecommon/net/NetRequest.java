package com.threesome.basecommon.net;


import com.threesome.basecommon.base.BaseBean;
import com.threesome.basecommon.base.BaseResponseBean;

import io.reactivex.Observable;

/**
 * author: L.K.X
 * created on: 2017/7/29 下午5:41
 * description:
 */

public abstract class NetRequest {
    private static NetRequest instance;
    protected NetRequest() {
        instance = this;
    }
    public static NetRequest getInstance() {
        return instance;
    }

    public abstract <T extends BaseResponseBean> Observable<T> request(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean);
    public abstract <T extends BaseResponseBean> Observable<T> requestThread(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean);
    public abstract <T extends BaseResponseBean> T call(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean);
}

