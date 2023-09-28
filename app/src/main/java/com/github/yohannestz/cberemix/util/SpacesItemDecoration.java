
package com.github.yohannestz.cberemix.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpacing;

    public SpacesItemDecoration(int spacing) {
        mSpacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        outRect.left = mSpacing;
        outRect.top = mSpacing;
        outRect.right = mSpacing;
        outRect.bottom = mSpacing;
    }
}
