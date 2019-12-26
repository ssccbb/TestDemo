package com.sung.testdemo.videoframes;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sung at 2019-12-19
 *
 * @Description:
 */
public class VideoFetchingSeekbar extends RelativeLayout {
    public static final String TAG = VideoFetchingSeekbar.class.getSimpleName();

    private int lastX, lastY;
    private long mVideoDuration;
    private String mVideoPath;
    private List<Bitmap> mThumbNails;

    private VideoHelper mVideoHelper;
    private VideoFetchingThumbAdapter mThumbAdapter;
    private OnProgressChangeListener listener;

    //宽高
    private int[] mPointerCoordinate;
    private int[] mPreviewCoordinate;

    //预览条
    private RecyclerView mThumbPreview;
    //滑块指针
    private View mThumbPointerContainer;
    private ImageView mThumbPointer;

    private VideoHelper.Callback<Long> getVideoDuration;
    private VideoHelper.Callback<List<Bitmap>> getThumbNails;

    public VideoFetchingSeekbar(Context context) {
        super(context);
        initView();
        initVar();
    }

    public VideoFetchingSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initVar();
    }

    public VideoFetchingSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initVar();
    }

    private void initVar() {
        mThumbNails = new ArrayList<>();
        mVideoHelper = VideoHelper.getInstance();
        mThumbAdapter = new VideoFetchingThumbAdapter(mThumbNails);
    }

    private void initView() {
        View root = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_view_video_fetching_seekbar, null, false);

        mThumbPreview = root.findViewById(R.id.rc_thumb_nails);
        mThumbPointerContainer = root.findViewById(R.id.ll_pointer);
        mThumbPointer = root.findViewById(R.id.iv_pointer);

        this.addView(root, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * @param path video路径（若为网络http开头）
     */
    public void bindVideoResource(String path) {
        mVideoPath = path;

        getThumbNails = new VideoHelper.Callback<List<Bitmap>>() {
            @Override
            public void complete(List<Bitmap> thumbNails) {
                mThumbNails.clear();
                if (thumbNails.isEmpty()) return;
                mThumbNails.add(thumbNails.get(0));
                mThumbNails.addAll(thumbNails);
                mThumbNails.add(thumbNails.get(thumbNails.size() - 1));
                //
                mThumbAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(String msg) {

            }
        };
        getVideoDuration = new VideoHelper.Callback<Long>() {
            @Override
            public void complete(Long duration) {
                mVideoDuration = duration;
            }

            @Override
            public void failed(String msg) {

            }
        };

        //获取视频长度
        mVideoHelper.getVideoDurationInBackStage(path, getVideoDuration);
        //获取帧图片list
        int count = getThumbCount();
        mVideoHelper.getVideoThumbInBackstage(path, count > 0 ? count : 1, getThumbNails);

        //
        setView();
    }

    private void setView() {
        mThumbPreview.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
        mThumbPreview.setNestedScrollingEnabled(false);
        mThumbPreview.setHasFixedSize(true);
        mThumbPreview.setItemAnimator(new DefaultItemAnimator());
        mThumbPreview.setAdapter(mThumbAdapter);

        //设置第一帧
        setThumbPointerBitmap(1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPointerCoordinate = measureView(mThumbPointerContainer);
        mPreviewCoordinate = measureView(mThumbPreview);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //不在滑块内不存触摸点
                if (!isTouchPointInView(mThumbPointerContainer, rawX, rawY)) break;
                //将点下的点的坐标保存
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                //当前无保存的触摸点不移动（以触摸点为移动标示是为了拖动过快情况离开滑块还可继续拖动）
                if (lastX == -1 && lastY == -1) break;
                //计算出需要移动的距离
                int dx = rawX - lastX;

                int mThumbWidth = mPointerCoordinate[0];
                int mThumbPointerLeft = mThumbPointerContainer.getLeft();
//                int mThumbPointerRight = mThumbPointerContainer.getRight();
                int mSeekbarLeft = this.getLeft();
                int mSeekbarRight = this.getRight();
                //将移动距离加上，现在本身距离边框的位置
                int left = mThumbPointerLeft + dx;
//                int right = mThumbPointerRight + dx;

                /*
                 * .getLeft()为获取当前view距离父容器左右距离
                 * 此处判断左边界过界：滑块getLeft()+seekbar.getLeft() < seekbar.getLeft()
                 *
                 * 实际在操作的时候leftmargin设置过多会导致滑块超出seekbar容器
                 * 此时.getRight()方法获取到的值恒=容器右边界距左边界的数值
                 * 此处判断右边界过界：滑块getLeft()+滑块宽+seekbar.getLeft() > seekbar.getRight()
                 *
                 * */
                if (left + mSeekbarLeft < mSeekbarLeft || left + mThumbWidth + mSeekbarLeft > mSeekbarRight)
                    //不超过左右临界点
                    //滑块是有一定宽度的recyclerview需要前后各插入两条占位数据才可使滑块可划到长度内的头和尾
                    //(为保证滑块中线为seekbar的起始线和结束线占位item宽度皆为滑块一半)
                    return true;
                //获取到layoutParams然后改变属性 在设置回去
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) mThumbPointerContainer.getLayoutParams();
                layoutParams.leftMargin = left;
                mThumbPointerContainer.setLayoutParams(layoutParams);
                if (listener != null) {
                    listener.onProgressChange(getPointerCurrentProgress());
                }
                //记录最后一次移动的位置
                lastX = rawX;
                lastY = rawY;

                break;
            case MotionEvent.ACTION_UP:
                //重置触摸点
                lastY = -1;
                lastX = -1;

                //set img
                setThumbPointerBitmap(getPointerCurrentProgress());
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * @param view 目标
     * @param x    手指触摸x坐标
     * @param y    y坐标
     * @return 触摸点在目标view内
     */
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }


    // set

    /**
     * @param res res
     */
    public void setThumbPointerResouce(int res) {
        mThumbPointer.setImageResource(res);
    }

    /**
     * @param currentTime 取帧时间戳(s)
     */
    public void setThumbPointerBitmap(long currentTime) {
        mVideoHelper.getVideoThumbInBackstage(mVideoPath, currentTime, new VideoHelper.Callback<Bitmap>() {
            @Override
            public void complete(Bitmap thumb) {
                mThumbPointer.setImageBitmap(thumb);
                if (listener != null) {
                    listener.onObtainBitmapComp(thumb);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    //get

    /**
     * list显示多少张截图
     */
    private int getThumbCount() {
        //多少张图
        int[] parms = measureView(this);
        int count = parms[0] / mPointerCoordinate[0];
        if (parms[0] % mPointerCoordinate[0] > 0) {
            count++;
        }
        return count - 1;
    }

    /**
     * @return 视频长度long
     */
    private long getVideoDuration() {
        return mVideoDuration <= 0 ? 0 : mVideoDuration;
    }

    /**
     * @return 滑块当前时间（video内时间以秒为单位）
     */
    private long getPointerCurrentProgress() {
        long currentProgress = 0;
        try {
            //总宽度
            int previewWidth = mPreviewCoordinate[0];
            //滑块宽度
            int pointerWidth = mPointerCoordinate[0];
            //去掉头尾占位获得真实长度
            int realTotalProgress = previewWidth - pointerWidth * 2;
            //以滑块中线为基准线 算基准线距离左方基准线宽度占比
            //滑块距左距离 - 占位距离 + 滑块的一半 = 滑块基准线距真实长度的距离
            int realPointerProgress = ((LayoutParams) mThumbPointerContainer.getLayoutParams()).leftMargin;
            //用微秒（宽度都是几百，秒算都是0）
            currentProgress = getVideoDuration() * 1000000 / (long) realTotalProgress * realPointerProgress;
        } catch (Exception e) {
            Log.e(TAG, "getPointerCurrentProgress: " + e.toString());
        }
        return currentProgress / 1000000;
    }

    public interface OnProgressChangeListener {
        /**
         * @param progressMS 当前秒
         */
        void onProgressChange(long progressMS);

        /**
         * @param currentFrame 当前帧图片
         */
        void onObtainBitmapComp(Bitmap currentFrame);
    }

    public void addOnProgressChangeListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }
}
