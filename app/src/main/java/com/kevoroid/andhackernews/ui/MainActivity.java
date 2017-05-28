package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;

public class MainActivity extends BaseActivity implements StoryListAdapter.StoryListAdapterInterface {

    private Fragment mStoryListFragment;
    private Fragment mStoryDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mStoryListFragment == null) {
            mStoryListFragment = StoryListFragment.newInstance();
            addFragment(mStoryListFragment);
        } else {
            addFragment(mStoryListFragment);
        }
    }

    protected void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mStoryListFragment.isVisible()) {
            finish();
        }
    }

    @Override
    public void onItemClick() {
        if (mStoryDetailFragment == null) {
            mStoryDetailFragment = StoryDetailFragment.newInstance();
            replaceFragment(mStoryDetailFragment);
        } else {
            replaceFragment(mStoryDetailFragment);
        }
    }
}
