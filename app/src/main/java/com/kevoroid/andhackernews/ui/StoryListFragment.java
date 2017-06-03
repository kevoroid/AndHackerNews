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
import com.google.gson.Gson;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.adapters.VerticalSpaceItemDecoration;
import com.kevoroid.andhackernews.api.RequestMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListFragment extends BaseFragment {

    public static final String TAG = "Kev_DEBUG";

    private StoryListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mPullDownRefreshLayout;
    private String cachedIds = null;
    private Object cachedIdsJSONArray = null;

    int pastVisibleItems, visibleItemCount, totalItemCount;

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
                refreshItems();
            }
        });
        mPullDownRefreshLayout.setRefreshing(true);

        // this causes last 2 cards to stick together. will debug later
        //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.activity_margin)));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        /*
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                System.out.println("visibleItemCount = " + visibleItemCount);
                System.out.println("totalItemCount = " + totalItemCount);
                System.out.println("pastVisibleItems = " + pastVisibleItems);
                System.out.println("dy = " + dy);
            }
        });
        */

        if (cachedIdsJSONArray == null) {
            getStories();
        } else {
            getItems(cachedIdsJSONArray);
        }
    }

    private void refreshItems() {
        mPullDownRefreshLayout.setRefreshing(true);
        cachedIdsJSONArray = null;
        AndHackerNewsController.getInstance(getContext()).getRequestQueue().cancelAll(BaseFragment.class);
        mAdapter.clearDataSet();
        getStories();
    }

    public void getStories() {
        RequestMaker requestMaker = new RequestMaker(Request.Method.GET, RequestMaker.HN_TOP_STORIES_URL, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                cachedIdsJSONArray = o;
                getItems(cachedIdsJSONArray);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "OOPS! Houston, We have a problem!", Toast.LENGTH_LONG).show();
                mPullDownRefreshLayout.setRefreshing(false);
            }
        });

        requestMaker.setTag(BaseFragment.class);
        AndHackerNewsController.getInstance(getContext()).addToRequestQueue(requestMaker);
    }

    public void getItems(final Object jsonArray) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.addAll(Arrays.asList(jsonArray.toString().replace("[[", "").replace("]]", "").split(",")));
        Log.d(TAG, "list fragment items array count: " + stringArrayList.size());
        // using this for all stories will take time to load all since its a huge array of ids, should be done with pagination via either RxJava or sharding the data
        for (int i = 0; i < stringArrayList.size(); i++) {
            RequestMaker requestMaker = new RequestMaker(Request.Method.GET, RequestMaker.HN_ITEM_URL + stringArrayList.get(i) + ".json", new Response.Listener() {
                @Override
                public void onResponse(Object o) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(o.toString().replace("[{", "{").replace("}]", "}"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mAdapter.addAdapterData(jsonObject);
                    mAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "OOPS! Houston, We have a problem!", Toast.LENGTH_LONG).show();
                    mPullDownRefreshLayout.setRefreshing(false);
                }
            });

            requestMaker.setTag(BaseFragment.class);
            AndHackerNewsController.getInstance(getContext()).addToRequestQueue(requestMaker);
        }
        mRecyclerView.setAdapter(mAdapter);
        mPullDownRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
