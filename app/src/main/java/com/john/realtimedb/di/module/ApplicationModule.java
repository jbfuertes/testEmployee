package com.john.realtimedb.di.module;

import android.app.Application;
import android.content.Context;

import com.john.realtimedb.App;
import com.john.realtimedb.di.qualifier.ApplicationContext;
import com.john.realtimedb.interactor.AppData;
import com.john.realtimedb.interactor.DataManager;
import com.john.realtimedb.repository.api.connection.ConnectionApi;
import com.john.realtimedb.repository.api.connection.ConnectionManager;
import com.john.realtimedb.repository.api.firebase.FirebaseDbApi;
import com.john.realtimedb.repository.api.firebase.FirebaseDbManager;
import com.john.realtimedb.utils.rx.AppSchedulerProvider;
import com.john.realtimedb.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by john on 3/5/18.
 */

@Module(includes = NetworkModule.class)
public class ApplicationModule {

    private App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return app;
    }

    @Provides
    Application provideApplication(){
        return app;
    }

    @Provides
    SchedulerProvider provideScheduler(){
        return new AppSchedulerProvider();
    }

    @Singleton
    @Provides
    DataManager provideDataManager(AppData appData){
        return appData;
    }

    @Singleton
    @Provides
    FirebaseDbManager provideFirebaseDbManager(FirebaseDbApi firebaseDbApi){
        return firebaseDbApi;
    }

    @Singleton
    @Provides
    ConnectionManager provideConnectionManager(ConnectionApi connectionApi){
        return connectionApi;
    }

    /*@Singleton
    @Provides
    PreferenceManager providePreferenceManager(AppPreference appPreference){
        return appPreference;
    }*/

}
