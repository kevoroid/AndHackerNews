package com.kevoroid.andhackernews.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by kevin on 5/28/17.
 */

public class RequestMaker<T> extends BaseRequest<T> {

    private final Response.Listener<T> mListener;

    public static final String HN_WEB_URL = "https://news.ycombinator.com";
    public static final String HN_API_URL = "https://hacker-news.firebaseio.com/v0/";
    public static final String HN_TOP_STORIES_URL = HN_API_URL + "topstories.json";
    public static final String HN_ITEM_URL = HN_API_URL + "/item/";

    public RequestMaker(int method, String url, Response.Listener<T> responseListener, Response.ErrorListener errorListener) {
        super(method, url, responseListener, errorListener);
        mListener = responseListener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String responseBody = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(networkResponse, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(T o) {
        mListener.onResponse(o);
    }
}
