package com.john.realtimedb.interactor;


import com.john.realtimedb.model.result.EmployeeResult;
import com.john.realtimedb.model.result.RegisterEmployeeResult;
import com.john.realtimedb.repository.HostSelectionInterceptor;
import com.john.realtimedb.repository.api.connection.ConnectionManager;
import com.john.realtimedb.repository.api.firebase.FirebaseDbManager;
import com.john.realtimedb.utils.CommonUtils;
import com.john.realtimedb.utils.rx.SchedulerProvider;


import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by john on 11/28/17.
 */

@Singleton
public class AppData implements DataManager{

    private final FirebaseDbManager firebaseDbManager;
    private final ConnectionManager connectionManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    HostSelectionInterceptor interceptor;

    @Inject
    AppData(
            FirebaseDbManager firebaseDbManager,
            ConnectionManager connectionManager,
            SchedulerProvider schedulerProvider) {
        this.firebaseDbManager = firebaseDbManager;
        this.connectionManager = connectionManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<EmployeeResult> loadEmployeeList(boolean load, boolean reload) {
        return connectionManager.checkStatus()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMapObservable(connected -> {
                    if (connected){
                        return firebaseDbManager.loadEmployees()
                                .map(list -> new EmployeeResult.Builder()
                                        .success(true)
                                        .withEmployeeList(list)
                                        .build());
                    }else {
                        return Observable.just(new EmployeeResult.Builder()
                                .withErrorMessage("No Connection")
                                .withEmployeeList(new ArrayList<>())
                                .build());
                    }
                })
                .onErrorReturn(t -> new EmployeeResult.Builder()
                        .withErrorMessage(t.getMessage())
                        .withEmployeeList(new ArrayList<>())
                        .build())
                .startWith(new EmployeeResult.Builder()
                        .inProgress(load)
                        .reloading(reload)
                        .withEmployeeList(new ArrayList<>())
                        .build());


    }

    @Override
    public Observable<Boolean> listen() {
        return connectionManager.listen()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    @Override
    public Observable<RegisterEmployeeResult> checkName(String firstName, String lastName) {
        return connectionManager.checkStatus()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMapObservable(connected -> {
                    if (connected){
                        return firebaseDbManager.checkEmployee(firstName,lastName)
                                .map(isRegistered -> new RegisterEmployeeResult.Builder()
                                        .withErrorMessage(isRegistered?"Employee Already Registered":null)
                                        .build())
                                .toObservable();
                    }else {
                        return Observable.just(new RegisterEmployeeResult.Builder()
                                .success(false)
                                .withErrorMessage("No Connection")
                                .build());
                    }
                })
                .onErrorReturn(t -> new RegisterEmployeeResult.Builder()
                        .withErrorMessage(t.getMessage())
                        .build())
                .startWith(new RegisterEmployeeResult.Builder()
                        .inProgress(true)
                        .build());
    }

    @Override
    public Observable<RegisterEmployeeResult> checkEmail(String email) {
        return Observable.just(CommonUtils.isEmailValid(email))
                .flatMap(isValid -> {
                    if (isValid){
                        return connectionManager.checkStatus()
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.ui())
                                .flatMapObservable(connected -> {
                                    if (connected){
                                        return firebaseDbManager.checkEmail(email)
                                                .map(emailAvailable -> new RegisterEmployeeResult.Builder()
                                                        .withErrorMessage(!emailAvailable?"Email already used":null)
                                                        .build())
                                                .toObservable();
                                    }else {
                                        return Observable.just(new RegisterEmployeeResult.Builder()
                                                .withErrorMessage("No Connection")
                                                .build());
                                    }

                                });
                    }else {
                        return Observable.just(new RegisterEmployeeResult.Builder()
                                .withErrorMessage("Not a valid email")
                                .build());
                    }
                })
                .onErrorReturn(t -> new RegisterEmployeeResult.Builder()
                        .withErrorMessage(t.getMessage())
                        .build())
                .startWith(new RegisterEmployeeResult.Builder()
                        .inProgress(true)
                        .build());
    }

    @Override
    public Observable<RegisterEmployeeResult> registerEmployee(String firstName, String lastName, String email) {
        return connectionManager.checkStatus()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMapObservable(connected -> {
                    if (connected){
                        return firebaseDbManager.registerEmployee(
                                firstName,
                                lastName,
                                email)
                                .toSingleDefault(new RegisterEmployeeResult.Builder()
                                        .success(true)
                                        .build())
                                .toObservable();
                    }else {
                        return Observable.just(new RegisterEmployeeResult.Builder()
                                .withErrorMessage("No Connection")
                                .build());
                    }
                })
                .onErrorReturn(t -> new RegisterEmployeeResult.Builder()
                        .withErrorMessage(t.getMessage())
                        .build())
                .startWith(new RegisterEmployeeResult.Builder()
                        .inProgress(true)
                        .build());
    }
}
