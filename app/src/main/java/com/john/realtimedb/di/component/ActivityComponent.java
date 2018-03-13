package com.john.realtimedb.di.component;

import com.john.realtimedb.di.module.ActivityModule;
import com.john.realtimedb.di.scope.PerActivity;
import com.john.realtimedb.ui.activities.ActivityAddEmployee;
import com.john.realtimedb.ui.activities.ActivityEmployeeList;

import dagger.Component;

/**
 * Created by john on 3/5/18.
 */

@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(ActivityEmployeeList activity);

    void inject(ActivityAddEmployee activity);
}
