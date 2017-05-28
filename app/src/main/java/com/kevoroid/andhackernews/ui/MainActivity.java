package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kevoroid.andhackernews.R;

public class MainActivity extends BaseActivity {

    private Fragment mStoryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mStoryListFragment == null) {
            mStoryListFragment = StoryListFragment.newInstance();
            setFragment(mStoryListFragment);
        } else {
            setFragment(mStoryListFragment);
        }
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mStoryListFragment.isVisible()) {
            finish();
        }
    }
}
