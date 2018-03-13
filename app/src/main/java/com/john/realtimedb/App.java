package com.john.realtimedb;

import android.support.multidex.MultiDexApplication;

import com.john.realtimedb.di.component.ApplicationComponent;
import com.john.realtimedb.di.component.DaggerApplicationComponent;
import com.john.realtimedb.di.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by john on 3/5/18.
 */

public class App extends MultiDexApplication{

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


    }

    public ApplicationComponent getAppComponent(){
        return appComponent;
    }

}
