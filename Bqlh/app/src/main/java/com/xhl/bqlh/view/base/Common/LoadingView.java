package com.xhl.bqlh.view.base.Common;

/**
 * Created by Sum on 16/6/20.
 */
public interface LoadingView {

    /**
     * 显示加载UI
     */
    void showLoading();

    /**
     * @param msg 提示信息
     */
    void showLoading(String msg);

    /**
     * @param msg        提示信息
     * @param cancelable 是否可以取消
     */
    void showLoading(String msg, boolean cancelable);

    /**
     * 隐藏加载UI
     */
    void hideLoading();

}
