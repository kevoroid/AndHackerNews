package com.kevoroid.andhackernews;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by kevin on 5/30/17.
 */

public class AndHackerNewsController {

    private static AndHackerNewsController mInstance;
    private RequestQueue mRequestQueue;

    private AndHackerNewsController(Context context) {
        initVolley(context);
    }

    public static synchronized AndHackerNewsController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AndHackerNewsController(context.getApplicationContext());
        }
        return mInstance;
    }

    private void initVolley(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
