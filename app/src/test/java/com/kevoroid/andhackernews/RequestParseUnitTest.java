package com.kevoroid.andhackernews;

import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.ui.StoryListFragment;

import org.json.JSONArray;
import org.json.JSONObject;
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

    @Test
    public void ResponseElementsTest() throws Exception {
        JSONObject jsonObject = new JSONObject(TestResponse.mainStoryItem);
        Assert.assertNotNull(jsonObject);

        StoryListAdapter storyListAdapter = new StoryListAdapter();

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_TITLE, 0));
        Assert.assertEquals(jsonObject.getString(StoryListAdapter.RESULT_FIELD_TITLE), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_TITLE, 0));

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_BY, 0));
        Assert.assertEquals(jsonObject.getString(StoryListAdapter.RESULT_FIELD_BY), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_BY, 0));

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_TIME, 0));
        Assert.assertEquals(String.valueOf(jsonObject.getInt(StoryListAdapter.RESULT_FIELD_TIME)), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_TIME, 0));

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_SCORE, 0));
        Assert.assertEquals(String.valueOf(jsonObject.getInt(StoryListAdapter.RESULT_FIELD_SCORE)), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_SCORE, 0));

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_URL, 0));
        Assert.assertEquals(jsonObject.getString(StoryListAdapter.RESULT_FIELD_URL), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "string", StoryListAdapter.RESULT_FIELD_URL, 0));

        Assert.assertNotNull(storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "array", StoryListAdapter.RESULT_FIELD_KIDS, 0));
        Assert.assertEquals(String.valueOf(jsonObject.getJSONArray(StoryListAdapter.RESULT_FIELD_KIDS)), storyListAdapter.returnObjectValueNamed(new JSONArray().put(jsonObject), "array", StoryListAdapter.RESULT_FIELD_KIDS, 0));
    }
}
