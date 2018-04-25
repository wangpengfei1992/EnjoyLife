package com.stx.xhb.enjoylife.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.meikoz.core.base.BaseFragment;
import com.stx.xhb.enjoylife.R;
import com.stx.xhb.enjoylife.model.entity.TuchongImagEntity;
import com.stx.xhb.enjoylife.presenter.tuchong.getFeedAppContact;
import com.stx.xhb.enjoylife.presenter.tuchong.getFeedAppPresenterImpl;
import com.stx.xhb.enjoylife.ui.adapter.ImageRecyclerAdapter;
import com.stx.xhb.enjoylife.ui.widget.RecyclerViewNoBugStaggeredGridLayoutManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by jxnk25 on 2016/12/2.
 *
 * @link https://xiaohaibin.github.io/
 * @email： xhb_199409@163.com
 * @github: https://github.com/xiaohaibin
 * @description： 电视直播
 */
public class TuChongFragment extends BaseFragment implements XRecyclerView.LoadingListener, getFeedAppContact.View {

    @Bind(R.id.recly_view)
    XRecyclerView mRvTuChong;
    private int page = 1;
    private String posId = "";
    private List<TuchongImagEntity.FeedListBean> imgList;
    private ImageRecyclerAdapter recyclerAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_common;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        mRvTuChong.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewNoBugStaggeredGridLayoutManger layoutManager = new RecyclerViewNoBugStaggeredGridLayoutManger(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvTuChong.setLayoutManager(layoutManager);
        mRvTuChong.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRvTuChong.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mRvTuChong.setArrowImageView(R.drawable.iconfont_downgrey);
        mRvTuChong.setLoadingListener(this);
        imgList = new ArrayList<>();
        recyclerAdapter = new ImageRecyclerAdapter(getActivity(), R.layout.item_list_picture, imgList);
        mRvTuChong.setAdapter(recyclerAdapter);
    }

    @Override
    public void onResponse(List<TuchongImagEntity.FeedListBean> feedList, boolean isMore) {
        onLoadComplete(page);
        posId = String.valueOf(feedList.get(feedList.size() - 1).getPost_id());
        imgList.addAll(feedList);
        mRvTuChong.setLoadingMoreEnabled(isMore);
    }

    @Override
    public void onFailure(String msg) {
        onLoadComplete(page);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onInitData2Remote() {
        super.onInitData2Remote();
        onRefresh();
    }

    @Override
    protected Class getLogicClazz() {
        return getFeedAppContact.class;
    }

    @Override
    public void onRefresh() {
        page = 1;
        ((getFeedAppPresenterImpl) mPresenter).getFeedAppImage(page, "refresh", posId);
    }

    @Override
    public void onLoadMore() {
        page++;
        ((getFeedAppPresenterImpl) mPresenter).getFeedAppImage(page, "loadmore", posId);
    }

    private void onLoadComplete(int page) {
        if (page == 1) {
            imgList.clear();
            mRvTuChong.refreshComplete();
        } else {
            mRvTuChong.loadMoreComplete();
        }
    }
}