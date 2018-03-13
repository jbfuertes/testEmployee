package com.john.realtimedb.presenter;

import com.john.realtimedb.model.action.CheckAction;
import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.SubmitAction;
import com.john.realtimedb.model.result.RegisterEmployeeResult;

import io.reactivex.ObservableTransformer;

/**
 * Created by john on 3/12/18.
 */

public interface AddEmployeePresenter extends BasePresenter {
    ObservableTransformer<CheckAction,RegisterEmployeeResult> checkNameAction();

    ObservableTransformer<SubmitAction,RegisterEmployeeResult> submitAction();

    ObservableTransformer<ListenAction,RegisterEmployeeResult> listenAction();
}
