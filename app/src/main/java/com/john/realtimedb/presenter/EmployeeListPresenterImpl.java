package com.john.realtimedb.presenter;


import com.john.realtimedb.interactor.DataManager;
import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.LoadAction;
import com.john.realtimedb.model.result.EmployeeResult;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;

/**
 * Created by john on 11/29/17.
 */

public class EmployeeListPresenterImpl extends BasePresenterImpl implements EmployeeListPresenter {

    @Inject
    EmployeeListPresenterImpl(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public ObservableTransformer<LoadAction, EmployeeResult> loadAction() {
        return loadAction -> loadAction
                .flatMap(action ->
                        getDataManager().loadEmployeeList(action.load,action.reload));
    }

    @Override
    public ObservableTransformer<ListenAction, EmployeeResult> listenAction() {
        return listenAction -> listenAction
                .flatMap(action -> getDataManager().listen())
                .filter(bool -> !bool)
                .map(bool -> new EmployeeResult.Builder()
                        .withEmployeeList(new ArrayList<>())
                        .withErrorMessage("No Connection")
                        .build());
    }
}
