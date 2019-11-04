package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kevoroid.andhackernews.R;

public class SettingsFragment extends BaseFragment {

	private ActionBar actionBar;

	public static SettingsFragment newInstance() {

		Bundle args = new Bundle();

		SettingsFragment fragment = new SettingsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getActivity() != null) {
			actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
		}
		if (actionBar != null) {
			actionBar.setTitle("Settings");
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.settings_fragment, container, false);
		return v;
	}
}