package com.john.realtimedb.view_model;

import com.john.realtimedb.model.ui_request.RegisterEmployeeEvent;
import com.john.realtimedb.model.ui_response.RegisterEmployeeUiModel;

import io.reactivex.ObservableTransformer;

/**
 * Created by john on 3/12/18.
 */

public interface AddEmployeeHandler extends BaseHandler {
    ObservableTransformer<RegisterEmployeeEvent,RegisterEmployeeUiModel> employeeEvents();
}
