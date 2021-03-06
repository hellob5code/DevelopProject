package com.xhl.bqlh.view.custom.viewPager.autoScrollViewPager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter
{

    private List<? extends View> pageViews;

    public ViewPagerAdapter(List<? extends View> pageViews)
    {
        super();
        this.pageViews = pageViews;
    }

    public void setData(List<? extends View> pageViews)
    {
        this.pageViews = pageViews;
    }

    @Override
    public int getCount()
    {
        return pageViews == null ? 0 : pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2)
    {
        try {
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取每一个item�?类于listview中的getview
     */
    @Override
    public Object instantiateItem(View arg0, int arg1)
    {
        ((ViewPager) arg0).addView(pageViews.get(arg1));
        return pageViews.get(arg1);
    }

}