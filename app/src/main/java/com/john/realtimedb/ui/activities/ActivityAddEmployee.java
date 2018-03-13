package com.john.realtimedb.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.john.realtimedb.R;
import com.john.realtimedb.model.ui_request.RegisterEmployeeEvent;
import com.john.realtimedb.model.ui_request.RegisterListenEvent;
import com.john.realtimedb.model.ui_request.SubmitEvent;
import com.john.realtimedb.model.ui_request.CheckEvent;
import com.john.realtimedb.view_model.AddEmployeeHandler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class ActivityAddEmployee extends BaseActivity {

    @Inject
    AddEmployeeHandler handler;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.firstNameText)
    EditText firstNameText;

    @BindView(R.id.lastNameText)
    EditText lastNameText;

    @BindView(R.id.emailText)
    EditText emailText;

    @BindView(R.id.addButton)
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        setUp();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void setUp() {
        getCompositeDisposable().add(mergeEvents()
                .compose(handler.employeeEvents())
                .subscribe(model -> {

                    progressBar.setVisibility(model.inProgress? View.VISIBLE:View.GONE);

                    addButton.setEnabled(
                            !model.inProgress&&
                            model.errorMessage==null&&
                            !firstNameText.getText().toString().isEmpty()&&
                            !lastNameText.getText().toString().isEmpty()&&
                            !emailText.getText().toString().isEmpty());

                    if (model.errorMessage!=null){
                        onError(model.errorMessage);
                    }

                    if (model.success){
                        finish();
                    }


                }));
    }

    private Observable<RegisterEmployeeEvent> mergeEvents(){
        return Observable.merge(
                checkEvent(),
                submitEvent(),
                listenEvent());
    }

    private Observable<RegisterListenEvent> listenEvent(){
        return Observable.just(new RegisterListenEvent());
    }

    private Observable<CheckEvent> checkEvent(){
        return Observable.combineLatest(
                RxTextView.textChanges(firstNameText)
                        .skipInitialValue()
                        .filter(s -> !s.toString().isEmpty()),
                RxTextView.textChanges(lastNameText)
                        .skipInitialValue()
                        .filter(s -> !s.toString().isEmpty()),
                RxTextView.textChanges(emailText)
                        .skipInitialValue()
                        .filter(s -> !s.toString().isEmpty()),
                (s,s1,s2) -> new CheckEvent(s.toString(),s1.toString(),s2.toString()));
    }

    private Observable<SubmitEvent> submitEvent(){
        return RxView.clicks(addButton)
                .map(__ -> new SubmitEvent(
                        firstNameText.getText().toString(),
                        lastNameText.getText().toString(),
                        emailText.getText().toString()));
    }


}
