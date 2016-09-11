package com.moge.gege.network.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

import com.moge.gege.config.NetworkConfig;
import com.moge.gege.model.RespLikeResultModel;
import com.moge.gege.network.util.BaseRequest;
import com.moge.gege.network.util.ResponseEventHandler;

/**
 * query topic like
 */
public class TopicLikeRequest extends BaseRequest<RespLikeResultModel>
{
    public TopicLikeRequest(int topicType, String topicId, String boardId,
            ResponseEventHandler<RespLikeResultModel> listener)
    {
        super(Method.POST, getRequestUrl(), getRequestParam(topicType, topicId,
                boardId), RespLikeResultModel.class, listener);
    }

    private static String getRequestUrl()
    {
        return NetworkConfig.generalAddress + "/v1/like";
    }

    private static String getRequestParam(int topicType, String topicId,
            String boardId)
    {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("tid", topicId));
        list.add(new BasicNameValuePair("topic_type", String.valueOf(topicType)));

        if (!TextUtils.isEmpty(boardId))
        {
            list.add(new BasicNameValuePair("bid", boardId));
        }

        return URLEncodedUtils.format(list, HTTP.UTF_8);
    }

}
