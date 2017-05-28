package com.kevoroid.andhackernews;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by kevin on 5/28/17.
 */

public class AndHackerNewsApplication extends Application {

    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
    }

    private void initVolley() {
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
