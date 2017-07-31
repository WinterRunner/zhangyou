package com.threesome.basecommon.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threesome.basecommon.R;
import com.threesome.uilibrary.widget.ContentLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午6:09
 * description:
 */

public abstract class BaseFragment extends RxFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.basecommon_fragment_base, null);
        initCreateView(view);
        return view;
    }

    private void initCreateView(View view) {
        ContentLayout contentLayout = (ContentLayout) view.findViewById(R.id.content_fragment_layout_bc);
        contentLayout.setContentView(View.inflate(getContext(),getLayout(),null));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();
        initView();
        initNetAndData();
    }

    protected abstract int getLayout();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initNetAndData();
}
