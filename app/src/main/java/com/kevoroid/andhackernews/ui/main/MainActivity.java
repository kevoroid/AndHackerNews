package com.kevoroid.andhackernews.ui.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.ui.BaseActivity;
import com.kevoroid.andhackernews.ui.storydetail.StoryDetailFragment;
import com.kevoroid.andhackernews.ui.storydetail.StoryWebViewFragment;

public class MainActivity extends BaseActivity implements StoryListAdapter.StoryListAdapterInterface {

    public static final String TYPE_CONSTANT_STORY = "story";
    public static final String TYPE_CONSTANT_COMMENT = "comment";

	private Fragment mStoryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		addMainFragment();
    }

	private void addMainFragment() {
		mStoryListFragment = StoryListFragment.newInstance();
		replaceFragment(mStoryListFragment);
	}

	@Override
	public void onItemClick(String type, String title, String url, int commentsCount, String commentsList) {
		switch (type) {
			case TYPE_CONSTANT_STORY:
				replaceFragment(StoryWebViewFragment.newInstance(title, url, commentsCount, commentsList));
				break;
			case TYPE_CONSTANT_COMMENT:
				replaceFragment(StoryDetailFragment.newInstance(title, commentsCount, commentsList));
				break;
		}
	}

	@Override
	public void onBackPressed() {
		if (mStoryListFragment.isVisible()) {
			finish();
		} else {
			super.onBackPressed();
		}
	}
}
