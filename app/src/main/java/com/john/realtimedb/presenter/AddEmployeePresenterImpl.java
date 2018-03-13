package com.john.realtimedb.presenter;

import com.john.realtimedb.interactor.DataManager;
import com.john.realtimedb.model.action.CheckAction;
import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.SubmitAction;
import com.john.realtimedb.model.result.RegisterEmployeeResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by john on 3/12/18.
 */

public class AddEmployeePresenterImpl extends BasePresenterImpl
        implements AddEmployeePresenter {

    @Inject
    AddEmployeePresenterImpl(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public ObservableTransformer<CheckAction, RegisterEmployeeResult> checkNameAction() {
        return checkNameAction -> checkNameAction
                .flatMap(action ->
                        Observable.zip(
                                getDataManager().checkName(
                                        action.firstName,
                                        action.lastName),
                                getDataManager().checkEmail(action.email),
                                (result,result1) -> new RegisterEmployeeResult.Builder()
                                        .withErrorMessage(result.errorMessage!=null?
                                                result.errorMessage:
                                                result1.errorMessage)
                                        .inProgress(result.inProgress||result1.inProgress)
                                        .build()));
    }

    @Override
    public ObservableTransformer<SubmitAction, RegisterEmployeeResult> submitAction() {
        return submitAction -> submitAction
                .flatMap(action -> getDataManager().registerEmployee(
                        action.firstName,
                        action.lastName,
                        action.email));
    }

    @Override
    public ObservableTransformer<ListenAction, RegisterEmployeeResult> listenAction() {
        return listenAction -> listenAction
                .flatMap(__ -> getDataManager().listen()
                        .map(connected -> new RegisterEmployeeResult.Builder()
                                .withErrorMessage(!connected?"No Connection":null)
                                .build()));
    }
}
