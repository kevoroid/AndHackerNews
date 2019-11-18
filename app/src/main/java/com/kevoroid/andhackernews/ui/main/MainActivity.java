package com.kevoroid.andhackernews.ui.main;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.ui.BaseActivity;
import com.kevoroid.andhackernews.ui.storydetail.StoryDetailFragment;

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

	private void viewStory(String url) {
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			builder.setToolbarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
		}
		builder.addDefaultShareMenuItem();
		builder.setStartAnimations(this, R.anim.transition_animation_slide_from_right, R.anim.transition_animation_slide_to_left);
		builder.setExitAnimations(this, R.anim.transition_animation_slide_from_left, R.anim.transition_animation_slide_to_right);
		builder.build().launchUrl(this, Uri.parse(url));
	}

	@Override
	public void onItemClick(String type, String title, String url, int commentsCount, String commentsList) {
		switch (type) {
			case TYPE_CONSTANT_STORY:
				viewStory(url);
				//replaceFragment(StoryWebViewFragment.newInstance(title, url, commentsCount, commentsList));
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
