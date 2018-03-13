package com.john.realtimedb.view_model;

import com.john.realtimedb.model.action.ListenAction;
import com.john.realtimedb.model.action.LoadAction;
import com.john.realtimedb.model.result.EmployeeResult;
import com.john.realtimedb.model.ui_request.EmployeeEvent;
import com.john.realtimedb.model.ui_request.ListListenEvent;
import com.john.realtimedb.model.ui_request.LoadListEvent;
import com.john.realtimedb.model.ui_response.EmployeeUiModel;
import com.john.realtimedb.presenter.EmployeeListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class EmployeeListHandlerImpl extends BaseHandlerImpl
        implements EmployeeListHandler {

    private EmployeeUiModel initialState = new EmployeeUiModel.Builder()
            .inProgress(false)
            .reloading(false)
            .success(false)
            .withEmployeeList(new ArrayList<>())
            .build();

    @Inject
    EmployeeListPresenter presenter;

    @Inject
    EmployeeListHandlerImpl() {

    }

    @Override
    public ObservableTransformer<EmployeeEvent, EmployeeUiModel> loadEvent() {
        return loadEvent -> loadEvent
                .publish(shared -> Observable.merge(
                        shared.ofType(LoadListEvent.class)
                                .map(event -> new LoadAction(event.load,event.reload))
                                .compose(presenter.loadAction()),
                        shared.ofType(ListListenEvent.class)
                                .map(event -> new ListenAction())
                                .compose(presenter.listenAction())))
                .scan(initialState,(state, result) -> {
                    List<EmployeeUiModel.Employee> tempList = new ArrayList<>();

                    for (EmployeeResult.Employee employee: result.employeeList){
                        tempList.add(new EmployeeUiModel.Employee.Builder()
                                .withEmail(employee.email)
                                .withFirstName(employee.firstName)
                                .withLastName(employee.lastName)
                                .build());
                    }

                    return new EmployeeUiModel.Builder()
                            .success(result.success)
                            .reloading(result.reloading)
                            .inProgress(result.inProgress)
                            .withErrorMessage(result.errorMessage)
                            .withEmployeeList(result.success?tempList:state.employeeList)
                            .build();

                });
    }
}
