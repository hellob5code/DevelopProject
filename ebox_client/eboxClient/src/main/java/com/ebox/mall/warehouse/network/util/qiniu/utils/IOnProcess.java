package com.ebox.mall.warehouse.network.util.qiniu.utils;

public interface IOnProcess
{
    public void onProcess(long current, long total);

    public void onFailure(QiniuException ex);
}
