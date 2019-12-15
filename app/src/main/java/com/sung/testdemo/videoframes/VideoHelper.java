package com.sung.testdemo.videoframes;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by sung at 2019-12-14
 *
 * @Description:
 */
public class VideoHelper {
    private Context context;
    private static final String LOCAL_FILE_PATH = Environment.getExternalStorageDirectory() + "/com.yr.huajian/image/";

    private static class Holder {
        private static VideoHelper helper = new VideoHelper();
    }

    public static VideoHelper getInstance() {
        return Holder.helper;
    }

    private String getLocalPath() {
        File f = new File(LOCAL_FILE_PATH);
        if (!f.exists()) f.mkdirs();
        return LOCAL_FILE_PATH;
    }

    private MediaMetadataRetriever getMediaRetriever(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        if (videoPath.startsWith("http")) {
            //请求头,有就put
            media.setDataSource(videoPath, new HashMap<>());
        } else {
            File videoFile = new File(videoPath);
            if (!videoFile.exists()) {
                Log.w(VideoHelper.class.getSimpleName(), "record: video file is not exists when record finish");
                return null;
            }
            media.setDataSource(videoPath);
        }
        return media;
    }

    public void getVideoThumbInBackstage(String path, int count, callback callback) {
        BackgroundTask task = new BackgroundTask(callback);
        task.execute(path, count);
    }

    /**
     * 获取视频总长度
     *
     * @param videoPath path网络地址要以http开头
     * @return 秒
     */
    public long getVideoDuration(String videoPath) {
        int seconds = 0;
        try {
            MediaMetadataRetriever media = getMediaRetriever(videoPath);
            if (media == null) {
                return 0;
            }

            // 取得视频的长度(单位为毫秒)
            String time = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            // 取得视频的长度(单位为秒)
            seconds = Integer.valueOf(time) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seconds;
    }

    /**
     * 获取视频截图
     *
     * @param videoPath path网络地址要以http开头
     * @param coverTime 第几秒
     * @return 截图保存路径
     */
    public String getVideoThumb(String videoPath, long coverTime) {
        String strCoverFilePath = null;
        try {
            MediaMetadataRetriever media = getMediaRetriever(videoPath);
            if (media == null) {
                return null;
            }
            Bitmap thumb = media.getFrameAtTime(coverTime * 1000000);//这个参数是微秒
            strCoverFilePath = getLocalPath() + "thumb" + coverTime + "_" + System.currentTimeMillis() + ".jpg";
            File f = new File(strCoverFilePath);
            if (f.exists()) f.delete();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strCoverFilePath;
    }

    /**
     * 获取视频截图s
     *
     * @param videoPath path网络地址要以http开头
     * @param count     截几张
     * @return 截图保存路径list
     */
    public List<String> getVideoThumb(String videoPath, int count) {
        List<String> paths = new ArrayList<>();
        try {
            MediaMetadataRetriever media = getMediaRetriever(videoPath);
            if (media == null) {
                return null;
            }

            //总长度
            int seconds = Integer.valueOf(media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
            //间隔
            int interval = seconds / count;
            //从第一帧开始
            int startTime = 1;
            String timeStamp = String.valueOf(System.currentTimeMillis());
            //按照数量等分视频长度截图
            while ((startTime + interval) < seconds) {
                Bitmap thumb = media.getFrameAtTime((long) (startTime * 1000000));//这个参数是微秒
                String strCoverFilePath = getLocalPath() + "thumb" + startTime + "_" + timeStamp + ".jpg";
                File f = new File(strCoverFilePath);
                if (f.exists()){
                    f.delete();
                }else {
                    f.createNewFile();
                }
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(f);
                thumb.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
                //do
                startTime = startTime + interval;
                paths.add(strCoverFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(VideoHelper.class.getSimpleName(), "getVideoThumb: " + e.toString());
        }
        return paths;
    }

    /**
     * 获取视频截图s
     *
     * @param videoPath path网络地址要以http开头
     * @param times     时刻（秒级别）
     * @return 截图保存路径list
     */
    public List<String> getVideoThumb(String videoPath, long... times) {
        List<String> paths = new ArrayList<>();
        try {
            MediaMetadataRetriever media = getMediaRetriever(videoPath);
            if (media == null) {
                return null;
            }

            String timeStamp = String.valueOf(System.currentTimeMillis());
            int seconds = Integer.valueOf(media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
            for (int i = 0; i < times.length; i++) {
                long time = times[i];
                if (time >= seconds) continue;
                Bitmap thumb = media.getFrameAtTime(time * 1000000);//这个参数是微秒
                String strCoverFilePath = getLocalPath() + "thumb" + time + "_" + timeStamp + ".jpg";
                File f = new File(strCoverFilePath);
                if (f.exists()) f.delete();
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(f);
                thumb.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
                paths.add(strCoverFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paths;
    }

    public static class BackgroundTask extends AsyncTask<Object, Integer, Object> {

        private callback callback;

        public BackgroundTask(VideoHelper.callback callback) {
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            String path = "";
            if (objects[0] instanceof String) {
                path = (String) objects[0];
            } else {
                return null;
            }
            if (objects.length == 1) {
                return VideoHelper.getInstance().getVideoDuration(path);
            }
            if (objects.length == 2) {
                if (objects[1] instanceof Integer) {
                    return VideoHelper.getInstance().getVideoThumb(path, (int) objects[1]);
                }
                if (objects[1] instanceof Long) {
                    return VideoHelper.getInstance().getVideoThumb(path, (long) objects[1]);
                }
            }
            if (objects.length > 2) {
                //多个long值数量不确定 需要补全
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (callback == null) return;
            if (o instanceof Long) {
                callback.complete((long) o);
            }
            if (o instanceof String) {
                callback.complete((String) o);
            }
            if (o instanceof ArrayList) {
                callback.complete((List<String>) o);
            }
        }
    }

    public interface callback<T> {
        void complete(T t);
    }
}
