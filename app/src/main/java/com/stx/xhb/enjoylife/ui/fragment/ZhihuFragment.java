package com.stx.xhb.enjoylife.ui.fragment;


import android.os.Bundle;

import com.xhb.core.base.BaseFragment;
import com.stx.xhb.enjoylife.R;

/**
 * 知乎Fragment
 */
public class ZhihuFragment extends BaseFragment {

    public static ZhihuFragment newInstance() {
        return new ZhihuFragment();
    }
    @Override
    protected int getLayoutResource() {
       return R.layout.fragment_zhihu;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }
}
