package com.kevoroid.andhackernews;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by kevin on 5/28/17.
 */

public class AndHackerNewsApplication extends Application {

    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
        LeakCanary.install(this);
    }

    private void initVolley() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
    }

    public static synchronized RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
