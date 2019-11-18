package com.kevoroid.andhackernews.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import androidx.preference.PreferenceManager;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.Commons;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.ui.about.AboutFragment;
import com.kevoroid.andhackernews.ui.settings.SettingsFragment;

public class BaseActivity extends AppCompatActivity {

	private AboutFragment aboutFragment;
	private SettingsFragment settingsFragment;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		aboutFragment = AboutFragment.newInstance();
		settingsFragment = SettingsFragment.newInstance();
	}

	@Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

	protected void addFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag()).commit();
	}

	protected void replaceFragment(Fragment fragment) {
		if (!fragment.isAdded()) {
			getSupportFragmentManager().beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag())
					.commit();
		}
	}

	/*
		Moved to BaseFragment in order to add toolbar to other fragments and back button.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main_menu_dark_mode:
				switchDarkMode();
				return true;
			case R.id.main_menu_about:
				replaceFragment(aboutFragment);
				return true;
			case R.id.main_menu_settings:
				replaceFragment(settingsFragment);
				return true;
			case android.R.id.home:
				//onBackPressed();
				getSupportFragmentManager().popBackStack();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("ApplySharedPref")
	private void switchDarkMode() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean themeB = !pref.getBoolean(Commons.PREFERENCES_KEY_NIGHT_MODE, false);
		SharedPreferences.Editor editor = pref.edit();

		if (themeB) {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
			editor.putBoolean(Commons.PREFERENCES_KEY_NIGHT_MODE, true).apply();
		} else {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
			editor.putBoolean(Commons.PREFERENCES_KEY_NIGHT_MODE, false).apply();
		}
	}

    @Override
    protected void onStop() {
        super.onStop();
        AndHackerNewsController.getInstance(this).getRequestQueue().cancelAll(BaseActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
