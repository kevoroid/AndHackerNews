package com.kevoroid.andhackernews.ui.settings;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.kevoroid.andhackernews.R;

public class SettingsFragment extends PreferenceFragmentCompat {

	private ActionBar actionBar;

	public static SettingsFragment newInstance() {

		Bundle args = new Bundle();

		SettingsFragment fragment = new SettingsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		//setPreferencesFromResource(R.xml.preferences, rootKey); // Not using this yet cause Settings layout is empty and provided via onCreateView();
		setHasOptionsMenu(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getActivity() != null) {
			actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		}
		if (actionBar != null) {
			actionBar.setTitle(R.string.label_settings);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.settings_fragment, container, false);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.overflow, menu);
	}
}
