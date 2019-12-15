package com.sung.testdemo.griddecoration;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {
    private List<String> gridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            gridData.add(String.valueOf(i));
        }

        RecyclerView grid = findViewById(R.id.grid);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setAdapter(new GridAdapter());
        grid.addItemDecoration(new GridDecoration(this));
        grid.setLayoutManager(new GridLayoutManager(this, gridData.size() / 2));
        grid.setHasFixedSize(true);
    }

    class GridAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GridHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_stream_score_board_grid_item, null, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof GridHolder) {
                GridHolder gridHolder = (GridHolder) holder;
                gridHolder.onBind(position);
            }
        }

        @Override
        public int getItemCount() {
            return gridData.size();
        }
    }

    class GridHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private LinearLayout.LayoutParams params;

        public GridHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            int height = dip2px(itemView.getContext(), 25);
            params = new LinearLayout.LayoutParams(height, height);
        }

        void onBind(int position) {
            text.setLayoutParams(params);
            text.setGravity(Gravity.CENTER);
            text.setText(gridData.get(position));
        }
    }

    class GridDecoration extends RecyclerView.ItemDecoration {
        private int dividerHeight;
        private Paint dividerPaint;

        public GridDecoration(Context context) {
            dividerPaint = new Paint();
            dividerPaint.setColor(context.getResources().getColor(android.R.color.transparent));
            dividerHeight = 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int pos = parent.getChildAdapterPosition(view);
            pos++;
            if (pos <= (gridData.size() / 2)){
                outRect.bottom = dividerHeight/2;
                outRect.right = dividerHeight;
                if (pos == gridData.size()/2){
                    outRect.right = 0;
                }
            }else {
                outRect.top = dividerHeight/2;
                outRect.right = dividerHeight;
                if (pos == gridData.size()){
                    outRect.right = 0;
                }
            }
        }
    }


    /**
     * dp转px
     *
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
