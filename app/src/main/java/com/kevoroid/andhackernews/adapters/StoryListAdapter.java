package com.kevoroid.andhackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevoroid.andhackernews.R;
import com.kevoroid.andhackernews.helpers.TimeHelper;
import com.kevoroid.andhackernews.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 5/27/17.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

    public static final String TAG = "Kev_DEBUG";
    public static final String VALUE_TYPE_STRING = "string";
    public static final String VALUE_TYPE_ARRAY = "array";

    public static final String RESULT_FIELD_TITLE = "title";
    public static final String RESULT_FIELD_BY = "by";
    public static final String RESULT_FIELD_TIME = "time";
    public static final String RESULT_FIELD_SCORE = "score";
    public static final String RESULT_FIELD_URL = "url";
    public static final String RESULT_FIELD_KIDS = "kids";

    private JSONArray tempAllItemsJsonArray;

    private StoryListAdapterInterface mStoryListAdapterInterface;

    public StoryListAdapter() {
    }

    public StoryListAdapter(Context context) {
        tempAllItemsJsonArray = new JSONArray();
        if (context instanceof StoryListAdapterInterface) {
            mStoryListAdapterInterface = (StoryListAdapterInterface) context;
        }
    }

    public void addAdapterData(JSONObject fetchedData) {
        //Log.d(TAG, "addAdapterData length of tempAllItemsJsonArray: " + tempAllItemsJsonArray.length());
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
        //Log.d(TAG, "story adapter onCreateViewHolder: ");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //Log.d(TAG, "story adapter onBindViewHolder: ");
        try {
            viewHolder.mPostTitle.setText(returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_TITLE, position));
            viewHolder.mPostInfo.setText(String.format("Posted by: %s at %s", returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_BY, position),
                    TimeHelper.returnActualDate(returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_TIME, position))));
            viewHolder.mPostScore.setText(returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_SCORE, position));
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
        //Log.d(TAG, "story adapter getItemCount: " + tempAllItemsJsonArray.length());
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
                mStoryListAdapterInterface.onItemClick(MainActivity.TYPE_CONSTANT_STORY,
                        returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_TITLE, position),
                        returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_URL, position),
                        returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position) != null ?
                                returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position).length() : 0,
                        returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position) != null ?
                                returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position).toString() : "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            try {
                mStoryListAdapterInterface.onItemClick(MainActivity.TYPE_CONSTANT_COMMENT,
                        returnObjectValueNamed(returnDefaultArrayObject(), VALUE_TYPE_STRING, RESULT_FIELD_TITLE, position), null,
                        returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position) != null ?
                                returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position).length() : 0,
                        returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position) != null ?
                                returnObjectValueNamed(returnDefaultArrayObject(), RESULT_FIELD_KIDS, position).toString() : "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public String returnObjectValueNamed(JSONArray array, String type, String valueName, int position) {
        try {
            switch (type) {
                case VALUE_TYPE_STRING:
                    return array.getJSONObject(position).optString(valueName, "N/A");
                case VALUE_TYPE_ARRAY:
                    return array.getJSONObject(position).optJSONArray(valueName).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray returnObjectValueNamed(JSONArray array, String valueName, int position) {
        try {
            return array.getJSONObject(position).optJSONArray(valueName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray returnDefaultArrayObject() {
        if (tempAllItemsJsonArray != null) {
            return tempAllItemsJsonArray;
        } else {
            return new JSONArray();
        }
    }

    public interface StoryListAdapterInterface {
        void onItemClick(String type, String title, String url, int commentsCount, String commentsList);
    }
}
