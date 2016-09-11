package com.ebox.mall.warehouse.network.util.qiniu.auth;

import com.ebox.mall.warehouse.network.util.qiniu.utils.IOnProcess;
import com.ebox.mall.warehouse.network.util.qiniu.utils.QiniuException;



public abstract class CallRet implements IOnProcess
{
    public void onInit(int flag)
    {
    }

    public abstract void onSuccess(byte[] body);

    public abstract void onFailure(QiniuException ex);

    public void onProcess(long current, long total)
    {
    }

    public void onPause(Object tag)
    {
    }
}
