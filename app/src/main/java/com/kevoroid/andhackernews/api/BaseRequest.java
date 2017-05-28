package com.kevoroid.andhackernews.api;

import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by kevin on 5/28/17.
 */

abstract public class BaseRequest extends Request {

    public BaseRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }
}
