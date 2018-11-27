package com.wanpiao.master.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecorationUtil {
    class SpacesItemDecorationRight extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecorationRight(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.right = space;
        }
    }

    class SpacesItemDecorationBottom extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecorationBottom(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }
}
