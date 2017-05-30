package com.kevoroid.andhackernews.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.adapters.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListFragment extends BaseFragment {

    public static final String TAG = "Kev_DEBUG";

    public static final String HN_WEB_URL = "https://news.ycombinator.com";
    public static final String HN_API_URL = "https://hacker-news.firebaseio.com/v0/";
    public static final String HN_TOP_STORIES_URL = HN_API_URL + "topstories.json";
    public static final String HN_ITEM_URL = HN_API_URL + "/item/";

    private StoryListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mPullDownRefreshLayout;
    JSONArray tempAllItemsJsonArray = new JSONArray();

    public static StoryListFragment newInstance() {
        Bundle args = new Bundle();
        StoryListFragment fragment = new StoryListFragment();
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
        setRetainInstance(true);
        getStories();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new StoryListAdapter(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.story_list_recyclerview);
        mPullDownRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.story_list_pull_down_refresh);
        mPullDownRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        mPullDownRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullDownRefreshLayout.setRefreshing(true);
                AndHackerNewsController.getInstance(getContext()).getRequestQueue().cancelAll(BaseFragment.class);
                getStories();
            }
        });

        if (savedInstanceState == null) {
            mPullDownRefreshLayout.setRefreshing(true);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.activity_margin)));
    }

    public void getStories() {
        JsonArrayRequest request = new JsonArrayRequest(HN_TOP_STORIES_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d(TAG, "list fragment response: " + jsonArray);
                getItems(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "OOPS! Houston, We have a problem!", Toast.LENGTH_LONG).show();
            }
        });

        request.setTag(BaseFragment.class);
        AndHackerNewsController.getInstance(getContext()).addToRequestQueue(request);
    }

    public void getItems(final JSONArray jsonArray) {
        Log.d(TAG, "list fragment items array count: " + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JsonObjectRequest jsonObjectRequest = null;
            try {
                Log.d(TAG, "list fragment item: " + HN_ITEM_URL + jsonArray.get(i) + ".json");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HN_ITEM_URL + jsonArray.get(i) + ".json", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "list fragment item responce: " + response);

                            JSONObject itemsObject = new JSONObject();
                            itemsObject.put("title", response.optString("title"));
                            itemsObject.put("author", response.optString("by"));
                            itemsObject.put("score", response.optString("score"));
                            itemsObject.put("timestamp", response.optString("time"));
                            itemsObject.put("url", response.optString("url"));

                            tempAllItemsJsonArray.put(itemsObject);

                            if (tempAllItemsJsonArray.length() == jsonArray.length()) {
                                mAdapter.setAdapterData(tempAllItemsJsonArray);
                                mRecyclerView.setAdapter(mAdapter);
                                mPullDownRefreshLayout.setRefreshing(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

            assert jsonObjectRequest != null;
            jsonObjectRequest.setTag(BaseFragment.class);
            AndHackerNewsController.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
