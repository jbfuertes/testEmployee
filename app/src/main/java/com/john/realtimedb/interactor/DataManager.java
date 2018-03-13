package com.john.realtimedb.interactor;


import com.john.realtimedb.model.result.EmployeeResult;
import com.john.realtimedb.model.result.RegisterEmployeeResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by john on 11/28/17.
 */

public interface DataManager {


    Observable<EmployeeResult> loadEmployeeList(boolean load, boolean reload);


    Observable<Boolean> listen();

    Observable<RegisterEmployeeResult> checkName(String firstName, String lastName);

    Observable<RegisterEmployeeResult> checkEmail(String email);

    Observable<RegisterEmployeeResult> registerEmployee(String firstName, String lastName, String email);
}
