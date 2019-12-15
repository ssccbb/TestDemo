package com.sung.testdemo.GridScoreBoard;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sung.testdemo.R;

/**
 * Create by sung at 2019/2/14
 *
 * @Description: 表格
 */
public class GridScoreHistoryView extends LinearLayout {
    private RecyclerView grid;
    private int height = 0;

    public GridScoreHistoryView(Context context) {
        super(context);
    }

    public GridScoreHistoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GridScoreHistoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = grid.getMeasuredHeight() - dp2px(5);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.view_grid_score_histtory, this, false);
        LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.addView(view, params);
        init();
    }

    private void init() {
        grid = this.findViewById(R.id.grid);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(this.getContext(), 6));
        grid.setAdapter(new GridScoreHistoryAdapter(5));
        grid.addItemDecoration(new GridScoreHistoryDecoration(1));
    }

    class GridScoreHistoryAdapter extends RecyclerView.Adapter<GridScoreHistoryHolder> {
        private int playersNumber;

        public GridScoreHistoryAdapter(int players) {
            this.playersNumber = players;
            if (this.playersNumber < 0) {
                this.playersNumber = 0;
            }
            if (this.playersNumber > 5) {
                this.playersNumber = 5;
            }
        }

        @NonNull
        @Override
        public GridScoreHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_grid_score_history_item, parent, false);
            return new GridScoreHistoryHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull GridScoreHistoryHolder holder, int position) {
            holder.onBind(position);
        }

        @Override
        public int getItemCount() {
            return 6 * playersNumber;
        }
    }

    class GridScoreHistoryHolder extends RecyclerView.ViewHolder {
        private View root;
        private TextView score;

        public GridScoreHistoryHolder(View itemView) {
            super(itemView);
            root = itemView;
            score = itemView.findViewById(R.id.text);
        }

        void onBind(int position) {
            // 重设宽高
            onLayout();
            // 选手编号游标
            int number = position / 6;
            // grid单行游标
            int index = position % 6;
            // 设置内容
            if (index == 0){
                SpannableString ss = new SpannableString((number +1)+"\n选手"+number);
                RelativeSizeSpan span = new RelativeSizeSpan(2f);
                ss.setSpan(span,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                score.setText(ss);
            }else {
                score.setText(String.valueOf(index));
            }
        }

        void onLayout() {
            if (height <= 0) return;
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height / 5);
            root.setLayoutParams(params);
        }
    }

    class GridScoreHistoryDecoration extends RecyclerView.ItemDecoration {
        private int width;

        public GridScoreHistoryDecoration(int width) {
            this.width = dp2px(width);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildLayoutPosition(view);
            if (position % 6 == 0) {
                outRect.left = width;
                outRect.right = width / 2;
            } else if (position % 5 == 0) {
                outRect.left = width / 2;
                outRect.right = width;
            } else {
                outRect.left = width / 2;
                outRect.right = width / 2;
            }
            outRect.bottom = width;
        }
    }

    private int dp2px(int width){
        float scale = GridScoreHistoryView.this.getContext()
                .getResources().getDisplayMetrics().density;
        return  (int) (width * scale + 0.5f);
    }
}
