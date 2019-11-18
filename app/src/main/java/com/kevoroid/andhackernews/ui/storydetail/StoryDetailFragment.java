package com.kevoroid.andhackernews.ui.storydetail;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.api.RequestMaker;

import com.kevoroid.andhackernews.ui.BaseFragment;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryDetailFragment extends BaseFragment {

    private static final String STORY_TITLE = "storyTitle";
    private static final String STORY_COMMENTS_COUNT = "storyCommentsCount";
    private static final String STORY_COMMENTS_LIST = "storyCommentsList";

    private ActionBar actionBar;
    private ViewGroup commentViewGroup;

    private int commentsTotal;

    public static StoryDetailFragment newInstance(String title, int commentsCount, String commentList) {
        Bundle args = new Bundle();
        StoryDetailFragment fragment = new StoryDetailFragment();
        args.putString(STORY_TITLE, title);
        args.putInt(STORY_COMMENTS_COUNT, commentsCount);
        args.putString(STORY_COMMENTS_LIST, commentList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.label_comments));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_detail_fragment, container, false);
        TextView storyTitle = (TextView) v.findViewById(R.id.story_detail_title);
        TextView storyCommentsCount = (TextView) v.findViewById(R.id.story_detail_comments);
        commentViewGroup = (ViewGroup) v.findViewById(R.id.story_detail_comments_container);
        if (getArguments() != null) {
            storyTitle.setText(getArguments().getString(STORY_TITLE));
            storyCommentsCount.setText(String.format("Number of comments: %s", String.valueOf(getArguments().getInt(STORY_COMMENTS_COUNT))));
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            commentsTotal = getArguments().getInt(STORY_COMMENTS_COUNT, 0);;
            getComments(getArguments().getString(STORY_COMMENTS_LIST, "N/A"));
        }
    }

    private void getComments(String x) {
        ArrayList<String> commentsIds = new ArrayList<>(Arrays.asList(x.replace("[", "").replace("]", "").split(",")));
        for (int i = 0; i < commentsTotal; i++) {
            JsonObjectRequest jsonObjectRequest = null;
            try {
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        RequestMaker.HN_ITEM_URL + commentsIds.get(i) + ".json",
                        null,
                        new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            View postCommentViews = LayoutInflater.from(getContext()).inflate(R.layout.comments_container_row, commentViewGroup, false);
                            TextView textViewBy = (TextView) postCommentViews.findViewById(R.id.comments_row_by);
                            TextView textViewText = (TextView) postCommentViews.findViewById(R.id.comments_row_text);
                            textViewBy.setText(response.optString("by", "unknown"));
                            textViewText.setText(Html.fromHtml(response.optString("text", getResources().getString(R.string.msg_no_comment))));
                            textViewText.setMovementMethod(LinkMovementMethod.getInstance());
                            textViewText.setLinksClickable(true);
                            commentViewGroup.addView(postCommentViews);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert jsonObjectRequest != null;
            jsonObjectRequest.setTag(BaseFragment.class);
            AndHackerNewsController.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.overflow, menu);
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
