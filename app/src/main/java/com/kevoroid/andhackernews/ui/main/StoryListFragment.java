package com.kevoroid.andhackernews.ui.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.api.RequestMaker;

import com.kevoroid.andhackernews.ui.BaseFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListFragment extends BaseFragment {

    private static final String TAG = "StoryListFragment";

    private ActionBar actionBar;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mAdapter = new StoryListAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.story_list_recyclerview);
        mPullDownRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.story_list_pull_down_refresh);
        mPullDownRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        mPullDownRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        // this causes last 2 cards to stick together. will debug later
        //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.activity_margin)));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
            // Is this necessary? (it refreshes the content everytime!)
            //getItems(cachedIdsJSONArray);
        }
    }

    private void refreshItems() {
        mPullDownRefreshLayout.setRefreshing(true);
        cachedIdsJSONArray = null;
        AndHackerNewsController.getInstance(getContext()).getRequestQueue().cancelAll(BaseFragment.class);
        mAdapter.clearDataSet();
        getStories();
    }

    private void getStories() {
        mPullDownRefreshLayout.setRefreshing(true);
        RequestMaker requestMaker = new RequestMaker(Request.Method.GET, RequestMaker.HN_TOP_STORIES_URL, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                cachedIdsJSONArray = o;
                getItems(cachedIdsJSONArray);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showErrMsgSomethingWentWrong();
                mPullDownRefreshLayout.setRefreshing(false);
            }
        });

        requestMaker.setTag(BaseFragment.class);
        AndHackerNewsController.getInstance(getContext()).addToRequestQueue(requestMaker);
    }

    private void getItems(final Object jsonArray) {
        ArrayList<String> stringArrayList = formatJsonObject(jsonArray);
        Log.d(TAG, "list fragment items array count: " + stringArrayList.size());
        // using this for all stories will take time to load all since its a huge array of ids, should be done with pagination via either RxJava or sharding the data
        // For now Im just getting the first 50
        for (int i = 0; i < 50; i++) {
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
                    showErrMsgSomethingWentWrong();
                    mPullDownRefreshLayout.setRefreshing(false);
                }
            });

            requestMaker.setTag(BaseFragment.class);
            AndHackerNewsController.getInstance(getContext()).addToRequestQueue(requestMaker);
        }
        // Why this is here? moved to onViewCreated
        //mRecyclerView.setAdapter(mAdapter);
        mPullDownRefreshLayout.setRefreshing(false);
    }

    public ArrayList<String> formatJsonObject(Object input) {
        return new ArrayList<>(Arrays.asList(input.toString().replace("[[", "").replace("]]", "").split(",")));
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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

    public void showErrMsgSomethingWentWrong() {
        Toast.makeText(getContext(), getResources().getString(R.string.msg_err_we_have_a_problem), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(false);
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
