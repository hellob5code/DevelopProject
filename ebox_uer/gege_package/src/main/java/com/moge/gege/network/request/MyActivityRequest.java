package com.moge.gege.network.request;

import com.moge.gege.config.NetworkConfig;
import com.moge.gege.model.RespTopicListModel;
import com.moge.gege.network.util.BaseRequest;
import com.moge.gege.network.util.ResponseEventHandler;

/**
 * query my activity
 */
public class MyActivityRequest extends BaseRequest<RespTopicListModel>
{
    public MyActivityRequest(String cursor,
            ResponseEventHandler<RespTopicListModel> listener)
    {
        super(Method.GET, getRequestUrl(cursor), "", RespTopicListModel.class,
                listener);
    }

    private static String getRequestUrl(String cursor)
    {
        return NetworkConfig.generalAddress + "/v1/user/activity/apply"
                + "?cursor=" + cursor;
    }

}
