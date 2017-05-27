package com.kevoroid.andhackernews.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kevin on 5/27/17.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpace;

    public VerticalSpaceItemDecoration(int verticalSpace) {
        mVerticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.top = mVerticalSpace;
        }
    }
}
