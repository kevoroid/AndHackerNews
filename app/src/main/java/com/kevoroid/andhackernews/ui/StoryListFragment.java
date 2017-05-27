package com.kevoroid.andhackernews.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.adapters.StoryListAdapter;
import com.kevoroid.andhackernews.adapters.VerticalSpaceItemDecoration;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListFragment extends BaseFragment {

    private StoryListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mPullDownRefreshLayout;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_list_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new StoryListAdapter();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.story_list_recyclerview);
        mPullDownRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.story_list_pull_down_refresh);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_view_space)));
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
