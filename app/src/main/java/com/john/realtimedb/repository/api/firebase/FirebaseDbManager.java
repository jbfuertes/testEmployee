package com.john.realtimedb.repository.api.firebase;

import com.john.realtimedb.model.result.EmployeeResult;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

/**
 * Created by john on 3/12/18.
 */

public interface FirebaseDbManager {
    Observable<List<EmployeeResult.Employee>> loadEmployees();

    Single<Boolean> checkEmployee(String firstName, String lastName);

    Single<Boolean> checkEmail(String email);

    Completable registerEmployee(String firstName, String lastName, String email);
}
