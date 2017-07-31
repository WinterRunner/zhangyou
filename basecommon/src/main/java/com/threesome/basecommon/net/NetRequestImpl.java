package com.threesome.basecommon.net;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.threesome.basecommon.base.BaseBean;
import com.threesome.basecommon.base.BaseResponseBean;
import com.threesome.basecommon.common.AppConstant;
import com.threesome.function.controller.AppManager;
import com.threesome.function.utils.encrypt.Md5Utils;

import java.io.IOException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: L.K.X
 * created on: 2017/7/15 42
 * description:
 */

public class NetRequestImpl extends NetRequest {

    @Override
    public <T extends BaseResponseBean> Observable<T> request(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean) {
        String requestJson = new Gson().toJson(requestBean);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);

        String sign = sign(requestJson);

        Object obj = new Retrofit.Builder()
                .client(genericClient(methodName, sign, requestJson))
                .baseUrl(AppConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())// 使用Gson作为数据转换器
                .build()
                .create(clazz_service);
        try {
            Method method = obj.getClass().getMethod(methodName, RequestBody.class);
            return ((Observable<T>) method.invoke(obj, requestBody))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends BaseResponseBean> Observable<T> requestThread(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean) {
        String requestJson = new Gson().toJson(requestBean);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);

        String sign = sign(requestJson);
        Object obj = new Retrofit.Builder()
                .client(genericClient(methodName, sign, requestJson))
                .baseUrl(AppConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())// 使用Gson作为数据转换器
                .build()
                .create(clazz_service);
        try {
            Method method = obj.getClass().getMethod(methodName, RequestBody.class);
            return ((Observable<T>) method.invoke(obj, requestBody));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public <T extends BaseResponseBean> T call(Class clazz_service, String methodName, Class<T> clazz_result, BaseBean requestBean) {
        String requestJson = new Gson().toJson(requestBean);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestJson);

        String sign = sign(requestJson);
        Object obj = new Retrofit.Builder()
                .client(genericClient(methodName, sign, requestJson))
                .baseUrl(AppConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())// 使用Gson作为数据转换器
                .build()
                .create(clazz_service);
        try {
            Method method = obj.getClass().getMethod(methodName, RequestBody.class);
            Call<T> call = (Call<T>) method.invoke(obj, requestBody);
            retrofit2.Response<T> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private OkHttpClient genericClient(final String methodName, final String sign, final String requestJson) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //请求和响应都可以在这里监听到

                        Request.Builder builder = chain.request().newBuilder();

                        //添加cookie
                        if (!TextUtils.isEmpty(AppManager.getInstance().getUserManager().getCookie())) {
                            builder.header("Cookie", AppManager.getInstance().getUserManager().getCookie());
                        }

                        Request request = builder
                                .addHeader("Content-Type", "application/json")
                                .header("Authorization", sign)
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
//                                .addHeader("Cookie", "add cookies here")
                                .build();


                        if (AppConstant.IS_DEBUG) {
                            Logger.e("requestHeader:(%s)\nrequestUrl:(%s)\n" + request.headers().toString(), methodName, request.url());
                            Logger.json(requestJson);
                        }


                        Response response = chain.proceed(request);


                        if (AppConstant.IS_DEBUG) {
                            Logger.e("responseHeader:(%s)\n" + response.headers().toString(), methodName);
                            //这里不能直接使用response.body().string()的方式输出日志
                            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
                            //个新的response给应用层处理
                            ResponseBody responseBody = response.peekBody(1024 * 1024);
                            Logger.json(responseBody.string());
                        }


                        //保存cookie
                        if (!response.headers("set-cookie").isEmpty()) {
                            Observable.just(response.header("set-cookie"))
                                    .map(new Function<String, String>() {
                                        @Override
                                        public String apply(@NonNull String s) throws Exception {
                                            String[] cookieArray = s.split(";");
                                            return cookieArray[0];
                                        }
                                    }).subscribe(new Consumer<String>() {
                                @Override
                                public void accept(@NonNull String cookie) throws Exception {
                                    AppManager.getInstance().getUserManager().setCookie(cookie);
                                }
                            });
                        }


                        return response;
                    }
                })
                .build();
//        OkHttp请求头通过拦截器添加Header，两种方式的不同
//                .header(key, val):如果key相同，最后一个val会将前面的val值覆盖
//                .addHeader(key, val):如果key相同，最后一个val不会将前面的val值覆盖，而是新添加一个Header
        return httpClient;
    }


    private String sign(String requestJson) {
        return Md5Utils.md5(requestJson + AppManager.getInstance().getRequestkey2(), "UTF-8");
    }
}
