package com.john.realtimedb.di.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.john.realtimedb.App;
import com.john.realtimedb.di.module.ApplicationModule;
import com.john.realtimedb.di.qualifier.ApplicationContext;
import com.john.realtimedb.interactor.DataManager;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by john on 3/5/18.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager datamanager();

    Gson gson();

    Picasso picasso();

}
