package com.john.realtimedb.repository.api.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.john.realtimedb.model.result.EmployeeResult;
import com.john.realtimedb.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by john on 3/12/18.
 */

@Singleton
public class FirebaseDbApi implements FirebaseDbManager {

    private final FirebaseDatabase database;

    private final DatabaseReference reference;

    @Inject
    FirebaseDbApi() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(AppConstants.REFERENCE_KEY);
    }

    @Override
    public Observable<List<EmployeeResult.Employee>> loadEmployees() {
        return Observable.create(emitter ->
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<EmployeeResult.Employee> employeeList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            employeeList.add(postSnapshot.getValue(EmployeeResult.Employee.class));
                            Timber.d(postSnapshot.getValue(EmployeeResult.Employee.class)!=null?postSnapshot.getValue(EmployeeResult.Employee.class).email:"");
                        }
                        emitter.onNext(employeeList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Single<Boolean> checkEmployee(String firstName, String lastName) {
        return Single.create(emitter ->
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        emitter.onSuccess(dataSnapshot.child(firstName+" "+lastName).exists());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Single<Boolean> checkEmail(String email) {
        return Single.create(emitter ->
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean emailAvailable = true;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (postSnapshot.child("email").getValue(String.class).equals(email)){
                                emailAvailable = false;
                                break;
                            }else {
                                emailAvailable = true;
                            }
                        }
                        emitter.onSuccess(emailAvailable);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Completable registerEmployee(String firstName, String lastName, String email) {
        return Completable.fromAction(() -> {
            Map<String,String> employee = new HashMap<>();

            employee.put("firstName",firstName);

            employee.put("lastName",lastName);

            employee.put("email",email);

            reference.child(firstName+" "+lastName).setValue(employee);

        });
    }
}
