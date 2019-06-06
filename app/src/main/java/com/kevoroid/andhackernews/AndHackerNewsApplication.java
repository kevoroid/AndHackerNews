package com.kevoroid.andhackernews;

import android.app.Application;

public class AndHackerNewsApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		//initLeakCanary();
	}

	/*
	private void initLeakCanary() {
		if (BuildConfig.DEBUG) {
			LeakCanary.install(this);
		}
	}

	 */
}
