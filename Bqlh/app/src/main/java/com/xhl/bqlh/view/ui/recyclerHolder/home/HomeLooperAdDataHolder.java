package com.xhl.bqlh.view.ui.recyclerHolder.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xhl.bqlh.model.AdInfoModel;
import com.xhl.bqlh.model.AdModel;
import com.xhl.bqlh.view.custom.viewPager.ImageModel;
import com.xhl.bqlh.view.custom.viewPager.ImagePageView;
import com.xhl.xhl_library.ui.recyclerview.RecyclerDataHolder;
import com.xhl.xhl_library.ui.recyclerview.RecyclerViewHolder;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * 顶部轮播
 */
public class HomeLooperAdDataHolder extends RecyclerDataHolder<AdModel> {

    ImagePageView mPageView;

    public HomeLooperAdDataHolder(AdModel data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, int position) {
        ImagePageView pageView = new ImagePageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, 300);
        pageView.setLayoutParams(params);
        AutoUtils.auto(pageView);
        mPageView = pageView;
        return new TopAdvViewHolder(pageView);
    }

    @Override
    public int getType() {
        return 1;
    }


    @Override
    public void onBindViewHolder(Context context, int position, RecyclerView.ViewHolder vHolder, AdModel data) {
        TopAdvViewHolder holder = (TopAdvViewHolder) vHolder;
        holder.setData(data);
    }

    public void startScroll() {
        if (mPageView != null) {
            mPageView.startScroll();
        }
    }

    public void stopScroll() {
        if (mPageView != null) {
            mPageView.stopScroll();
        }
    }

    public void onDestroy() {
        if (mPageView != null) {
            mPageView.destory();
        }
    }

    static class TopAdvViewHolder extends RecyclerViewHolder implements ImagePageView.ImagePageViewListener {

        private ImagePageView pageView;

        private AdModel adModel;

        public TopAdvViewHolder(View view) {
            super(view);
            pageView = (ImagePageView) view;
        }

        public void setData(final AdModel data) {
            if (adModel == data) {
                return;
            }
            pageView.setListener(this);
            adModel = data;
            pageView.setDataSource(data.getAdvertList());
            pageView.startScroll();
        }

        @Override
        public void onImagePageClick(ImageModel model) {
            if (model instanceof AdInfoModel) {
                AdInfoModel.postEvent((AdInfoModel) model);
            }
        }
    }
}
