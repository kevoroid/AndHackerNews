package com.kevoroid.andhackernews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevoroid.andhackernews.R;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListAdapter extends BaseRecyclerViewAdapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPostTitle;
        private TextView mPostInfo;

        public ViewHolder(View viewItem) {
            super(viewItem);
            mPostTitle = (TextView) viewItem.findViewById(R.id.story_item_title);
            mPostInfo = (TextView) viewItem.findViewById(R.id.story_item_info);
        }
    }

}
