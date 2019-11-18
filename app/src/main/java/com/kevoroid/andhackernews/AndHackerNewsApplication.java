package com.kevoroid.andhackernews;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class AndHackerNewsApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		checkNightMode();
	}

	private void checkNightMode() {
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Commons.PREFERENCES_KEY_NIGHT_MODE, false)) {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		} else {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}
	}
}
