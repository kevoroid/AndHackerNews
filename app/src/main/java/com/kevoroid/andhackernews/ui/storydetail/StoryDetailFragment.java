package com.kevoroid.andhackernews.ui.storydetail;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private ActionBar actionBar;
    private ViewGroup commentViewGroup;

    public static StoryDetailFragment newInstance(String title, int commentsCount, String commentList) {
        Bundle args = new Bundle();
        StoryDetailFragment fragment = new StoryDetailFragment();
        args.putString("storyTitle", title);
        args.putInt("storyCommentsCount", commentsCount);
        args.putString("storyCommentsList", commentList);
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

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setTitle("Comments");
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
            storyTitle.setText(getArguments().getString("storyTitle"));
            storyCommentsCount.setText(String.format("Number of comments: %s", String.valueOf(getArguments().getInt("storyCommentsCount"))));
        }
        getComments(getArguments().getString("storyCommentsList"));
        return v;
    }

    public void getComments(String x) {
        int b = getArguments().getInt("storyCommentsCount");
        ArrayList<String> commentsIds = new ArrayList<>();
        commentsIds.addAll(Arrays.asList(x.replace("[", "").replace("]", "").split(",")));
        for (int i = 0; i < b; i++) {
            JsonObjectRequest jsonObjectRequest = null;
            try {
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RequestMaker.HN_ITEM_URL + commentsIds.get(i) + ".json", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            View postCommentViews = LayoutInflater.from(getContext()).inflate(R.layout.comments_container_row, commentViewGroup, false);
                            TextView textViewBy = (TextView) postCommentViews.findViewById(R.id.comments_row_by);
                            TextView textViewText = (TextView) postCommentViews.findViewById(R.id.comments_row_text);
                            textViewBy.setText(response.optString("by", "unknown"));
                            textViewText.setText(Html.fromHtml(response.optString("text", "no comment!")));
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
