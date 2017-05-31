package com.kevoroid.andhackernews.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;

import org.json.JSONArray;

public class MainActivity extends BaseActivity implements StoryListAdapter.StoryListAdapterInterface {

    public static final String TYPE_CONSTANT_STORY = "story";
    public static final String TYPE_CONSTANT_COMMENT = "comment";

    private Fragment mStoryListFragment;
    private Fragment mStoryDetailFragment;
    private Fragment mStoryWebViewFragment;

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
        getSupportFragmentManager().beginTransaction().add(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag()).commit();
    }

    protected void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_activity_fragment_container, fragment).addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mStoryListFragment.isVisible()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(String type, String title, String url, int commentsCount, String commentsList) {
        switch (type) {
            case TYPE_CONSTANT_STORY:
                mStoryWebViewFragment = StoryWebViewFragment.newInstance(title, url, commentsCount, commentsList);
                replaceFragment(mStoryWebViewFragment);
                break;
            case TYPE_CONSTANT_COMMENT:
                mStoryDetailFragment = StoryDetailFragment.newInstance(title, commentsCount, commentsList);
                replaceFragment(mStoryDetailFragment);
                break;
        }
    }
}
