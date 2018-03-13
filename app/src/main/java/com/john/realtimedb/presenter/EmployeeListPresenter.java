package com.john.realtimedb.presenter;

import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.LoadAction;
import com.john.realtimedb.model.result.EmployeeResult;

import io.reactivex.ObservableTransformer;

/**
 * Created by john on 11/29/17.
 */

public interface EmployeeListPresenter extends BasePresenter {

    ObservableTransformer<LoadAction,EmployeeResult> loadAction();

    ObservableTransformer<ListenAction,EmployeeResult> listenAction();
}
