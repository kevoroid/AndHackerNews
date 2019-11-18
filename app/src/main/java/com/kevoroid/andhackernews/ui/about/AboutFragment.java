package com.kevoroid.andhackernews.ui.about;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.ui.BaseFragment;

public class AboutFragment extends BaseFragment {

	private ActionBar actionBar;

	public static AboutFragment newInstance() {

		Bundle args = new Bundle();

		AboutFragment fragment = new AboutFragment();
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
			actionBar.setTitle(getResources().getString(R.string.label_about));
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about_fragment, container, false);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.overflow, menu);
	}
}
