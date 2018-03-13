package com.john.realtimedb.interactor;

import com.john.realtimedb.repository.api.connection.ConnectionManager;
import com.john.realtimedb.repository.api.firebase.FirebaseDbManager;
import com.john.realtimedb.utils.RxSchedulersOverrideRule;
import com.john.realtimedb.utils.rx.AppSchedulerProvider;
import com.john.realtimedb.utils.rx.SchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by john on 3/13/18.
 */


@RunWith(MockitoJUnitRunner.class)
public class AppDataTest {

    @Rule
    public RxSchedulersOverrideRule schedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    ConnectionManager connectionManager;

    @Mock
    FirebaseDbManager firebaseDbManager;

    private SchedulerProvider schedulerProvider;

    private DataManager dataManager;

    @Before
    public void setUp() throws Exception{
        schedulerProvider = new AppSchedulerProvider();
        dataManager = new AppData(firebaseDbManager,connectionManager,schedulerProvider);

    }

    @Test
    public void testConnection(){

        TestObserver<Boolean> observer = TestObserver.create();

        when(connectionManager.listen()).thenReturn(Observable.just(true,false,true));

        dataManager.listen().subscribe(observer);

        observer.assertValueCount(3);

        observer.assertValueAt(0,true);

        observer.assertValueAt(1,false);

        observer.assertValueAt(2,true);

        observer.assertNoErrors();

        observer.awaitTerminalEvent();

        observer.assertComplete();


    }

    @Test
    public void testMail(){

        String usedEmail = "abc@gmail.com";

        String unUsedEmail = "def@gmail.com";

        String invalidEmail = "asdad";

        when(connectionManager.checkStatus()).thenReturn(Single.just(true));

        when(firebaseDbManager.checkEmail("abc@gmail.com")).thenReturn(Single.just(false));

        when(firebaseDbManager.checkEmail("def@gmail.com")).thenReturn(Single.just(true));

        dataManager.checkEmail(usedEmail)
                .lastElement()
                .test()
                .assertValue(item -> item.errorMessage.equals("Email already used"));

        dataManager.checkEmail(unUsedEmail)
                .lastElement()
                .test()
                .assertValue(item -> item.errorMessage == null);

        dataManager.checkEmail(invalidEmail)
                .lastElement()
                .test()
                .assertValue(item -> item.errorMessage.equals("Not a valid email"));

        dataManager.checkEmail(unUsedEmail)
                .firstElement()
                .test()
                .assertValue(item -> item.inProgress);

    }

    @Test
    public void testName(){
        String registeredFirstName = "jay";

        String registeredLastName = "lim";

        String unRegisteredFirstName = "may";

        String unRegisteredLastName = "lim";

        when(connectionManager.checkStatus()).thenReturn(Single.just(true));

        when(firebaseDbManager.checkEmployee(registeredFirstName,registeredLastName))
                .thenReturn(Single.just(true));

        when(firebaseDbManager.checkEmployee(unRegisteredFirstName,unRegisteredLastName))
                .thenReturn(Single.just(false));

        dataManager.checkName(registeredFirstName,registeredLastName)
                .lastElement()
                .test()
                .assertValue(item -> item.errorMessage.equals("Employee Already Registered"));

        dataManager.checkName(unRegisteredFirstName,unRegisteredLastName)
                .lastElement()
                .test()
                .assertValue(item -> item.errorMessage == null);

        dataManager.checkName(unRegisteredFirstName,unRegisteredLastName)
                .firstElement()
                .test()
                .assertValue(item -> item.inProgress);


    }

    @Test
    public void testLoadEmployees(){

        when(connectionManager.checkStatus()).thenReturn(Single.just(true));

        when(firebaseDbManager.loadEmployees())
                .thenReturn(Observable.just(new ArrayList<>()));

        dataManager.loadEmployeeList(true,false)
                .lastElement()
                .test()
                .assertValue(item -> item.success);

        dataManager.loadEmployeeList(true,false)
                .firstElement()
                .test()
                .assertValue(item -> item.inProgress);

        dataManager.loadEmployeeList(false,true)
                .firstElement()
                .test()
                .assertValue(item -> item.reloading);


    }

    @Test
    public void testRegisterEmployee(){

        String firstName = "takenFirstName";

        String lastName = "takenLastName";

        String email = "abc@gmail.com";

        when(connectionManager.checkStatus()).thenReturn(Single.just(true));

        when(firebaseDbManager.registerEmployee(firstName,lastName,email))
                .thenReturn(Completable.complete());

        dataManager.registerEmployee(firstName,lastName,email)
                .lastElement()
                .test()
                .assertValue(item -> item.success);

        dataManager.registerEmployee(firstName,lastName,email)
                .firstElement()
                .test()
                .assertValue(item -> item.inProgress);



    }

}