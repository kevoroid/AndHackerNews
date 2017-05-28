package com.kevoroid.andhackernews.api;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

/**
 * Created by kevin on 5/28/17.
 */

public class RequestMaker extends BaseRequest {

    public RequestMaker(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected void deliverResponse(Object o) {

    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
