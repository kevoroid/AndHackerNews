package com.kevoroid.andhackernews.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 5/28/17.
 */

public class ObjectParser extends BaseParser {

    public ObjectParser() {
    }

    public StoryItemModel returnParsedData(JSONObject jsonObject) throws JSONException {
        StoryItemModel storyItemModel = new StoryItemModel();
        storyItemModel.author = jsonObject.getString("by");
        storyItemModel.id = jsonObject.getString("id");
        storyItemModel.comments = jsonObject.getString("kids");
        storyItemModel.timestamp = jsonObject.getString("time");
        storyItemModel.title = jsonObject.getString("title");
        storyItemModel.url = jsonObject.getString("url");
        return storyItemModel;
    }
}
