package com.q1.your_music_collection;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alfo06-07 on 2018-05-21.
 */

public class CustomRecyclerDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public CustomRecyclerDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getPaddingLeft() != space) {
            parent.setPadding(space, space, space, space);
            parent.setClipToPadding(false);
        }

        outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }


}
