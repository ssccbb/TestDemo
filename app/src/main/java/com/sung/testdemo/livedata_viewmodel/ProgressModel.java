package com.sung.testdemo.livedata_viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Create by sung at 2018/12/3
 *
 * @Description:
 */
public class ProgressModel extends ViewModel {
    MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getProgress() {
        if (progress == null){
            progress = new MutableLiveData<>();
        }
        return progress;
    }
}
