package com.threesome.zhangyou;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.threesome.basecommon.base.BaseActivity;
import com.threesome.basecommon.net.NetRequest;
import com.threesome.function.controller.AppManager;
import com.threesome.function.utils.Sout;
import com.threesome.function.utils.StatusBarUtils;
import com.threesome.function.utils.encrypt.DES3;
import com.threesome.uilibrary.widget.ContentLayout;
import com.threesome.ximageview.GyroscopeManager;
import com.threesome.ximageview.XImageView;
import com.threesome.zhangyou.test.RequestBean;
import com.threesome.zhangyou.test.Result;
import com.threesome.zhangyou.test.TestService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity {


    private GyroscopeManager gyroscopeManager;

    @Override
    protected int getLayout() {
        return R.layout.app_activity_main;
    }

    @Override
    protected void initTitle() {
        StatusBarUtils.setStatusBarDisable(this);
    }

    @Override
    protected void initView() {
        contentLayout.setCurrentState(ContentLayout.ResultState.SUCCESS);
        gyroscopeManager = new GyroscopeManager();
        XImageView xiv_main = (XImageView) findViewById(R.id.xiv_main);
        xiv_main.setGyroscopeManager(gyroscopeManager);
    }

    @Override
    protected void initNetAndData() {
        RequestBean requestBean = new RequestBean();

        requestBean.username = DES3.encode("13552386771@qq", AppManager.getInstance().getRequestkey());
        requestBean.password = DES3.encode("qwer@123", AppManager.getInstance().getRequestkey());

        NetRequest.getInstance().requestThread(TestService.class, "getList", Result.class, requestBean)
                .compose(this.<Result>bindToLifecycle())
                .flatMap(new Function<Result, Observable<Result>>() {
                    @Override
                    public Observable<Result> apply(@NonNull Result result) throws Exception {
                        System.out.println("thread:"+Thread.currentThread().getName()+"======");
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result result) {
                        String json = new Gson().toJson(result);
                        Sout.print("========" + json);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Sout.print("===error=====");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Sout.print("===complete=====");
                    }
                });


    }


    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeManager.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeManager.unregister();
    }

    @Override
    public boolean onTitleEnable() {
        return false;
    }
}
