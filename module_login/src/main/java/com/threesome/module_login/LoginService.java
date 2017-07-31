package com.threesome.module_login;

import com.threesome.basecommon.base.BaseBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * author: L.K.X
 * created on: 2017/7/15 下午6:13
 * description:
 */

public interface LoginService {
    @GET("info?")
    Observable<BaseBean> getListInfo(@QueryMap Map<String, Object> options);


    @POST("save")
    Observable<BaseBean> saveInfo(@QueryMap Map<String, Object> options);
}
