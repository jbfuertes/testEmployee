package com.john.realtimedb.view_model;

import com.john.realtimedb.model.action.CheckAction;
import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.SubmitAction;
import com.john.realtimedb.model.ui_request.CheckEvent;
import com.john.realtimedb.model.ui_request.RegisterEmployeeEvent;
import com.john.realtimedb.model.ui_request.RegisterListenEvent;
import com.john.realtimedb.model.ui_request.SubmitEvent;
import com.john.realtimedb.model.ui_response.RegisterEmployeeUiModel;
import com.john.realtimedb.presenter.AddEmployeePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by john on 3/12/18.
 */

public class AddEmployeeHandlerImpl extends BaseHandlerImpl
        implements AddEmployeeHandler {

    private final AddEmployeePresenter presenter;

    private RegisterEmployeeUiModel initialState =
            new RegisterEmployeeUiModel
                    .Builder()
                    .build();

    @Inject
    public AddEmployeeHandlerImpl(AddEmployeePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ObservableTransformer<RegisterEmployeeEvent, RegisterEmployeeUiModel> employeeEvents() {
        return employeeEvents -> employeeEvents
                .publish(shared -> Observable.merge(
                        shared.ofType(CheckEvent.class)
                                .map(event -> new CheckAction(
                                        event.firstName,
                                        event.lastName,
                                        event.email))
                                .compose(presenter.checkNameAction()),
                        shared.ofType(SubmitEvent.class)
                                .map(event -> new SubmitAction(
                                        event.firstName,
                                        event.lastName,
                                        event.email))
                                .compose(presenter.submitAction()),
                        shared.ofType(RegisterListenEvent.class)
                                .map(event -> new ListenAction())
                                .compose(presenter.listenAction())))
                .scan(initialState,(state,result) -> {
                    return new RegisterEmployeeUiModel.Builder()
                            .inProgress(result.inProgress)
                            .success(result.success)
                            .withErrorMessage(result.errorMessage)
                            .build();
                });
    }
}
