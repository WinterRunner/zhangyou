package com.threesome.basecommon.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.threesome.basecommon.R;
import com.threesome.uilibrary.widget.ContentLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * author: L.K.X
 * created on: 2017/7/15 上午10:55
 * description:
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected ContentLayout contentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basecommon_activity_base);
        initPre();
        initTitle();
        initView();
        initNetAndData();
    }

    private void initPre() {
        if(onTitleEnable()){
            ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_title);
            viewStub.inflate();

            iv_back = (ImageView) findViewById(R.id.iv_back_bc);
            tv_title_center = (TextView) findViewById(R.id.tv_title_center_bc);
            tv_title_right = (TextView) findViewById(R.id.tv_title_right_bc);

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        contentLayout = (ContentLayout) findViewById(R.id.content_layout_bc);
        contentLayout.setContentView(View.inflate(this,getLayout(),null));
    }




    private ImageView iv_back;
    private TextView tv_title_center,tv_title_right;

    public boolean onTitleEnable(){
        return true;
    }


    public ImageView getIv_back() {
        return iv_back;
    }

    public TextView getTv_title_center() {
        return tv_title_center;
    }

    public TextView getTv_title_right() {
        return tv_title_right;
    }



    protected abstract int getLayout();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initNetAndData();
}
