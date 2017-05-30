package com.kevoroid.andhackernews;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by kevin on 5/28/17.
 */

public class AndHackerNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
