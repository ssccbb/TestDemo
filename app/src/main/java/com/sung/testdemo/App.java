package com.sung.testdemo;

import android.app.Application;

/**
 * Create by sung at 2019-12-14
 *
 * @Description:
 */
public class App extends Application {
    private static App instance = null;

    public static App getInstance(){
        if (instance == null) instance = new App();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
