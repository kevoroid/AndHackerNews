package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;

/**
 * Created by kevin on 5/27/17.
 */

public class BaseFragment extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main_menu_about:
				replaceFragment(AboutFragment.newInstance());
				break;
			case R.id.main_menu_settings:
				replaceFragment(SettingsFragment.newInstance());
				break;
			case android.R.id.home:
				if (getFragmentManager() != null) {
					getFragmentManager().popBackStack();
				}
		}
		return super.onOptionsItemSelected(item);
	}

	protected void addFragment(Fragment fragment) {
		if (getActivity() != null) {
			getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag()).commit();
		}
	}

	protected void replaceFragment(Fragment fragment) {
		if (getActivity() != null) {
			getActivity().getSupportFragmentManager().beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag())
					.commit();
		}
	}

    @Override
    public void onStop() {
        super.onStop();
        AndHackerNewsController.getInstance(getContext()).getRequestQueue().cancelAll(BaseFragment.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
