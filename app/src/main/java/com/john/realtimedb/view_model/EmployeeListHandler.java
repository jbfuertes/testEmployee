package com.john.realtimedb.view_model;

import com.john.realtimedb.model.ui_request.EmployeeEvent;
import com.john.realtimedb.model.ui_response.EmployeeUiModel;

import io.reactivex.ObservableTransformer;

public interface EmployeeListHandler extends BaseHandler {

    ObservableTransformer<EmployeeEvent,EmployeeUiModel> loadEvent();
}
