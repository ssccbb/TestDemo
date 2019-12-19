package com.sung.testdemo.videoframes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sung.testdemo.R;

import java.util.List;

/**
 * Create by sung at 2019-12-19
 *
 * @Description:
 */
public class VideoFetchingThumbAdapter extends RecyclerView.Adapter {
    private List<Bitmap> thumbNails;

    public VideoFetchingThumbAdapter(List<Bitmap> thumbNails) {
        this.thumbNails = thumbNails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoFetchingThumbHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_video_fetching_thumb_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VideoFetchingThumbHolder) holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return thumbNails.size();
    }

    public class VideoFetchingThumbHolder extends RecyclerView.ViewHolder {
        private ImageView thumb;

        public VideoFetchingThumbHolder(View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.iv_pointer);
        }

        void onBind(int position) {
            thumb.setImageBitmap(thumbNails.get(position));

            onLayoutChange(position);
        }

        void onLayoutChange(int position) {
            boolean halfWithItem = (position == 0 || position == thumbNails.size() - 1);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(dp2px(halfWithItem ? 20 : 40), dp2px(50)));
        }
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
