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
import com.kevoroid.andhackernews.api.StoryItemModel;
import com.kevoroid.andhackernews.helpers.TimeHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

//    private AdapterView.OnItemClickListener mListener;

    public static final String TAG = "Kev_DEBUG";

    private StoryListAdapterInterface mStoryListAdapterInterface;
    private Context mContext;
    private JSONArray mStoryListArray;
    public StoryItemModel mStoryItemModel;
    public ArrayList arrayList;

    public StoryListAdapter(Context context) {
        mContext = context;
        if (context instanceof StoryListAdapterInterface) {
            mStoryListAdapterInterface = (StoryListAdapterInterface) context;
        }
//        getStories();
    }

//    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
//        mListener = listener;
//    }

    //    public void setAdapterData(JSONArray adapterData, StoryItemModel storyItemModel) {
//    public void setAdapterData(ArrayList m) {
    public void setAdapterData(JSONArray adapterData) {
        mStoryListArray = adapterData;
//        mStoryItemModel = storyItemModel;
//        arrayList = m;
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
//            viewHolder.mPostTitle.setText(mStoryListArray.getString(position));
//            viewHolder.mPostTitle.setText(mStoryItemModel.title);
//            viewHolder.mPostInfo.setText(String.format("Post by: %s at %s", mStoryItemModel.author, TimeHelper.returnActualDate(mStoryItemModel.timestamp)));

//            viewHolder.mPostTitle.setText(arrayList.get(position).toString());

            viewHolder.mPostTitle.setText(mStoryListArray.getJSONObject(position).getString("title"));
            viewHolder.mPostInfo.setText(String.format("Posted by: %s at %s",
                    mStoryListArray.getJSONObject(position).getString("author"), TimeHelper.returnActualDate(mStoryListArray.getJSONObject(position).getString("timestamp"))));
            viewHolder.mPostScore.setText(mStoryListArray.getJSONObject(position).getString("score"));
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
        return mStoryListArray.length();
//        return arrayList.size();
    }

    //    public class ViewHolder extends RecyclerView.ViewHolder {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView mPostTitle;
        public TextView mPostInfo;
        public TextView mPostScore;

        public ViewHolder(View viewItem) {
            super(viewItem);
            mPostTitle = (TextView) viewItem.findViewById(R.id.story_item_title);
            mPostInfo = (TextView) viewItem.findViewById(R.id.story_item_info);
            mPostScore = (TextView) viewItem.findViewById(R.id.story_item_score);

//            viewItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            mListener.onItemClick(itemView, position);
//                        }
//                    }
//                }
//            });

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mStoryListArray.getJSONObject(position).getString("url")));
                mContext.startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            mStoryListAdapterInterface.onItemClick();
            return true;
        }
    }

    public interface StoryListAdapterInterface {
        void onItemClick();
    }
}
