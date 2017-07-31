package com.threesome.zhangyou.test;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * author: L.K.X
 * created on: 2017/7/15 下午6:13
 * description:
 */

public interface TestService {
//    @POST("portal/api/phases/eventphasesnomination/findapplist")
//    Observable<Result> getList(@QueryMap Map<String, Object> options);

    @POST("portal/data/test")
    Observable<Result> getList(@Body RequestBody route);
}
