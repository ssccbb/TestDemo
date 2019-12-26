package com.sung.testdemo.videoframes;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Create by sung at 2019-12-26
 *
 * @Description:
 */
public class VideoListAdapter extends RecyclerView.Adapter {
    private ArrayList<HashMap<String, String>> data;
    private OnItemClickListener listener;

    private ViewGroup.LayoutParams params;

    public VideoListAdapter(ArrayList<HashMap<String, String>> data) {
        this.data = new ArrayList<>();
        if (data != null && data.size() != 0)
            this.data.addAll(data);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_list_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VideoHolder) holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView preview;
        private TextView name;
        private TextView duration;
        private TextView path;

        public VideoHolder(View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.iv_preview);
            name = itemView.findViewById(R.id.tv_name);
            duration = itemView.findViewById(R.id.tv_duration);
            path = itemView.findViewById(R.id.tv_path);
        }

        void onBind(int position) {
            try {
                HashMap<String, String> map = data.get(position);
                String key_path = map.get("key_path");
                String key_name = map.get("key_name");
                String key_date = map.get("key_date");
                String key_duration = map.get("key_duration");
                name.setText(key_name);
                duration.setText(key_duration);
                path.setText(key_path);
                preview.setImageResource(R.color.colorPrimary);
                VideoHelper.getInstance().getVideoThumbInBackstage(key_path, (long) 100, new VideoHelper.Callback<Bitmap>() {
                    @Override
                    public void complete(Bitmap bitmap) {
                        preview.setImageBitmap(bitmap);
                    }

                    @Override
                    public void failed(String msg) {
                        preview.setImageResource(R.color.colorPrimary);
                    }
                });
                itemView.setOnClickListener(v -> {
                    if (listener != null) listener.onItemClick(key_path);
                });

                itemView.setLayoutParams(params);
            } catch (Exception e) {
                Log.e(VideoListAdapter.class.getSimpleName(), e.toString());
            }
        }
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String path);
    }
}
