package com.kevoroid.andhackernews.api;

import android.os.Parcel;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryItemModel extends BaseObject {

    public String author;
    public String id;
    public String comments;
    public String timestamp;
    public String title;
    public String url;

    public StoryItemModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.id);
        dest.writeString(this.comments);
        dest.writeString(this.timestamp);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected StoryItemModel(Parcel in) {
        this.author = in.readString();
        this.id = in.readString();
        this.comments = in.readString();
        this.timestamp = in.readString();
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<StoryItemModel> CREATOR = new Creator<StoryItemModel>() {
        @Override
        public StoryItemModel createFromParcel(Parcel source) {
            return new StoryItemModel(source);
        }

        @Override
        public StoryItemModel[] newArray(int size) {
            return new StoryItemModel[size];
        }
    };
}
