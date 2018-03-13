package com.john.realtimedb.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.john.realtimedb.di.qualifier.ActivityContext;
import com.john.realtimedb.di.scope.PerActivity;
import com.john.realtimedb.presenter.AddEmployeePresenter;
import com.john.realtimedb.presenter.AddEmployeePresenterImpl;
import com.john.realtimedb.presenter.EmployeeListPresenter;
import com.john.realtimedb.presenter.EmployeeListPresenterImpl;
import com.john.realtimedb.utils.rx.AppSchedulerProvider;
import com.john.realtimedb.utils.rx.SchedulerProvider;
import com.john.realtimedb.view_model.AddEmployeeHandler;
import com.john.realtimedb.view_model.AddEmployeeHandlerImpl;
import com.john.realtimedb.view_model.EmployeeListHandler;
import com.john.realtimedb.view_model.EmployeeListHandlerImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by john on 3/5/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    EmployeeListPresenter provideSplashPresenter(EmployeeListPresenterImpl presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    EmployeeListHandler provideSplashHandler(EmployeeListHandlerImpl handler){
        return handler;
    }

    @Provides
    @PerActivity
    AddEmployeePresenter provideEmployeePresenter(AddEmployeePresenterImpl presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    AddEmployeeHandler provideEmployeeHandler(AddEmployeeHandlerImpl handler){
        return handler;
    }

}
