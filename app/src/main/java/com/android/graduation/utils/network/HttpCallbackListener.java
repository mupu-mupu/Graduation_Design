package com.android.graduation.utils.network;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
