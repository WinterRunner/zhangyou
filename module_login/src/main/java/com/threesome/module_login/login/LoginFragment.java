package com.threesome.module_login.login;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.threesome.basecommon.base.BaseFragment;
import com.threesome.module_login.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午6:03
 * description:
 */

public class LoginFragment extends BaseFragment{

    @Override
    protected int getLayout() {
        return R.layout.fragment_login_lg;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        Glide.with(this)
                .load(R.mipmap.bg)
                .bitmapTransform(new BlurTransformation(getContext(),2,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView);
    }

    @Override
    protected void initNetAndData() {

    }
}
