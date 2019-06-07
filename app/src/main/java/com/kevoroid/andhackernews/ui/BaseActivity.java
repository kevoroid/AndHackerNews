package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;

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
