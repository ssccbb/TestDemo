package com.sung.testdemo.autocolumngridview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sung at 2019/5/24
 *
 * @Description: 自适应宽高以及列数的Grid
 */
public class AutoColumnGridRecyclerView extends RecyclerView {
    /**
     * 宽高
     */
    private int width, height;
    /**
     * 源数据
     */
    private List<String> data;

    public AutoColumnGridRecyclerView(Context context) {
        super(context);
        init();
    }

    public AutoColumnGridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        data = new ArrayList<>();
        width = 0;
        height = 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //异步获取宽高 初始化RecyclerView
        this.post(new Runnable() {
            @Override
            public void run() {
                updateLayoutSize(caculate(data.size()));
                setItemAnimator(new DefaultItemAnimator());
                setHasFixedSize(true);
                setLayoutManager(new GridLayoutManager(getContext(), caculate(data.size())));
                setAdapter(new GridAdapter());
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //拦截非GridAdapter
        if (!(adapter instanceof GridAdapter)) {
            return;
        }
        super.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        //拦截非GridLayoutManager
        if (!(layout instanceof GridLayoutManager)) {
            return;
        }
        super.setLayoutManager(layout);
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return (GridLayoutManager) super.getLayoutManager();
    }

    @Override
    public GridAdapter getAdapter() {
        return (GridAdapter) super.getAdapter();
    }

    /*
     * 更新宽高值
     * */
    private void updateLayoutSize(int column) {
        if (column <= 1) return;
        width = this.getWidth() / column;
        height = this.getHeight() / column;
    }

    /*
     * 计算列数
     * */
    private int caculate(int size) {
        if (size <= 3) return size > 0 ? size : 1;
        int column = 1;
        while (column * column < size) {
            column++;
        }
        return column;
    }

    /*
     * 更新源数据
     * */
    public void setData(List<String> list, boolean clear) {
        try {
            if (clear) data.clear();
            data.addAll(list);
            int column = caculate(data.size());
            updateLayoutSize(column);
            getLayoutManager().setSpanCount(column);
            getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(AutoColumnGridRecyclerView.class.getSimpleName(), "setData: " + e.toString());
        }
    }

    /**
     * 适用于Grid布局的Adapter
     */
    class GridAdapter extends RecyclerView.Adapter {

        public GridAdapter() {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GridHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_auto_grid_item_holder, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            GridHolder holder1 = (GridHolder) holder;
            holder1.onBind(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    /**
     * 适用于Grid布局的ViewHolder
     */
    class GridHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public GridHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        void onBind(int position) {
            textView.setText(data.get(position));
            if (width != 0 && height != 0) {
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                params.width = width;
                params.height = height;
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
