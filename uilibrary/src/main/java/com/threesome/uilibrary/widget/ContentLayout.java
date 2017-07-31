package com.threesome.uilibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.threesome.uilibrary.R;


/**
 * author: L.K.X
 * created on: 2017/7/14 下午6:09
 * description:
 */
public class ContentLayout extends FrameLayout {

    public enum ResultState {
        LOADING,
        SUCCESS,
        ERROR,
        EMPTY
    }

    private ResultState currentState = ResultState.LOADING;

    private View loadView;
    private View errorView;
    private View emptyView;
    private FrameLayout contentView;


    public ContentLayout(Context context) {
        this(context, null);
    }

    public ContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.uilibrary_view_content_layout, this);
        setCurrentState(ResultState.LOADING);
    }

    //控制该显示哪个view
    public void setCurrentState(ResultState currentState) {
        this.currentState = currentState;
        switch (currentState) {
            case LOADING:
                if (loadView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_loading);
                    viewStub.inflate();
                    loadView = findViewById(R.id.view_loading_bc);
                }
                loadView.setVisibility(VISIBLE);
                break;
            case SUCCESS:
                if (loadView != null)
                    loadView.setVisibility(INVISIBLE);
                if (errorView != null)
                    errorView.setVisibility(INVISIBLE);
                if (emptyView != null)
                    emptyView.setVisibility(INVISIBLE);


                if (contentView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_content);
                    viewStub.inflate();
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    contentView = (FrameLayout) findViewById(R.id.fl_container_content);
                    if (customeContentView == null) {
                        throw new RuntimeException("还没有设置布局");
                    } else {
                        contentView.addView(customeContentView, layoutParams);
                    }
                }
                contentView.setVisibility(VISIBLE);
                break;
            case ERROR:
                if (loadView != null)
                    loadView.setVisibility(INVISIBLE);
                if (emptyView != null)
                    emptyView.setVisibility(INVISIBLE);
                if (contentView != null)
                    contentView.setVisibility(INVISIBLE);


                if (errorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_error);
                    viewStub.inflate();
                    errorView = findViewById(R.id.view_error_bc);
                }
                errorView.setVisibility(VISIBLE);
                break;
            case EMPTY:
                if (loadView != null)
                    loadView.setVisibility(INVISIBLE);
                if (errorView != null)
                    errorView.setVisibility(INVISIBLE);
                if (contentView != null)
                    contentView.setVisibility(INVISIBLE);


                if (emptyView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_empty);
                    viewStub.inflate();
                    emptyView = findViewById(R.id.view_empty_bc);
                }
                emptyView.setVisibility(VISIBLE);
                break;
        }
    }

    private View customeContentView;

    public void setContentView(View customeContentView) {
        this.customeContentView = customeContentView;
    }
}
