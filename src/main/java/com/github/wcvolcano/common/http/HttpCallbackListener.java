package com.github.wcvolcano.common.http;

/**
 * Created by wencan on 2015/2/26.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
