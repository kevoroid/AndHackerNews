package com.kevoroid.andhackernews.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevoroid.andhackernews.R;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryDetailFragment extends BaseFragment {

    public static StoryDetailFragment newInstance(String title, int commentsCount) {
        Bundle args = new Bundle();
        StoryDetailFragment fragment = new StoryDetailFragment();
        args.putString("storyTitle", title);
        args.putInt("storyCommentsCount", commentsCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_detail_fragment, container, false);
        TextView storyTitle = (TextView) v.findViewById(R.id.story_detail_title);
        TextView storyCommentsCount = (TextView) v.findViewById(R.id.story_detail_comments);
        if (getArguments() != null) {
            storyTitle.setText(getArguments().getString("storyTitle"));
            storyCommentsCount.setText(String.format("Number of comments: %s", String.valueOf(getArguments().getInt("storyCommentsCount"))));
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
