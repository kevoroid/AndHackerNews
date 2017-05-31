package com.kevoroid.andhackernews.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.helpers.TimeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

    public static final String TAG = "Kev_DEBUG";

    private JSONArray tempAllItemsJsonArray = new JSONArray();

    private StoryListAdapterInterface mStoryListAdapterInterface;
    private Context mContext;

    public StoryListAdapter(Context context) {
        mContext = context;
        if (context instanceof StoryListAdapterInterface) {
            mStoryListAdapterInterface = (StoryListAdapterInterface) context;
        }
    }

    public void addAdapterData(JSONObject fetchedData) {
        Log.d(TAG, "addAdapterData length of tempAllItemsJsonArray: " + tempAllItemsJsonArray.length());
        tempAllItemsJsonArray.put(fetchedData);
    }

    public void clearDataSet() {
        if (tempAllItemsJsonArray.length() > 0) {
            tempAllItemsJsonArray = new JSONArray();
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "story adapter onCreateViewHolder: ");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "story adapter onBindViewHolder: ");
        try {
            viewHolder.mPostTitle.setText(tempAllItemsJsonArray.getJSONObject(position).optString("title", "No TITLE provided"));
            viewHolder.mPostInfo.setText(String.format("Posted by: %s at %s", tempAllItemsJsonArray.getJSONObject(position).optString("by", "unknown"),
                    TimeHelper.returnActualDate(tempAllItemsJsonArray.getJSONObject(position).optString("time")))
            );
            viewHolder.mPostScore.setText(tempAllItemsJsonArray.getJSONObject(position).optString("score", "-0-"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "story adapter getItemCount: " + tempAllItemsJsonArray.length());
        return tempAllItemsJsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mPostTitle;
        private TextView mPostInfo;
        private TextView mPostScore;

        private ViewHolder(View viewItem) {
            super(viewItem);
            mPostTitle = (TextView) viewItem.findViewById(R.id.story_item_title);
            mPostInfo = (TextView) viewItem.findViewById(R.id.story_item_info);
            mPostScore = (TextView) viewItem.findViewById(R.id.story_item_score);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tempAllItemsJsonArray.getJSONObject(position).optString("url")));
                mContext.startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            try {
                mStoryListAdapterInterface.onItemClick(tempAllItemsJsonArray.getJSONObject(position).optString("title"),
                        tempAllItemsJsonArray.getJSONObject(position).optJSONArray("kids") != null ? tempAllItemsJsonArray.getJSONObject(position).optJSONArray("kids").length() : 0,
                        tempAllItemsJsonArray.getJSONObject(position).optJSONArray("kids") != null ? tempAllItemsJsonArray.getJSONObject(position).optJSONArray("kids") : new JSONArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public interface StoryListAdapterInterface {
        void onItemClick(String title, int commentsCount, JSONArray commentsList);
    }
}
