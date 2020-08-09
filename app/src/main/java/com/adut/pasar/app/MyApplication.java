package com.adut.pasar.app;

import android.app.Application;

import com.adut.pasar.app.di.AppComponent;
import com.adut.pasar.app.di.AppModule;
import com.adut.pasar.app.di.DaggerAppComponent;

public class MyApplication extends Application {

    public static AppComponent appComponent;
    private static MyApplication mInstance;

    public static MyApplication getInstance(){
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initDI();
    }

    private void initDI() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
