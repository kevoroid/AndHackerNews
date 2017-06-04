package com.kevoroid.andhackernews;

import com.kevoroid.andhackernews.ui.StoryListFragment;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kevin on 6/3/17.
 */

public class RequestParseUnitTest {

    @Test
    public void RequestMakerTester() {
        StoryListFragment storyListFragment = new StoryListFragment();
        String tempArrayString = storyListFragment.formatJsonObject(TestResponse.mainStoriesIds.replace(" ", "")).toString();
        Assert.assertEquals(TestResponse.convertToArray(), tempArrayString);
    }
}
