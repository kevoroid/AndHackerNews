package com.kevoroid.andhackernews.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by kevin on 5/28/17.
 */

abstract public class BaseRequest<T> extends Request<T> {

    public BaseRequest(int method, String url, Response.Listener<T> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected void deliverResponse(T o) {
    }
}
