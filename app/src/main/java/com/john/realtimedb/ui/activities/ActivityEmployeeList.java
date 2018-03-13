package com.john.realtimedb.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.john.realtimedb.R;
import com.john.realtimedb.model.ui_request.EmployeeEvent;
import com.john.realtimedb.model.ui_request.ListListenEvent;
import com.john.realtimedb.model.ui_request.LoadListEvent;
import com.john.realtimedb.ui.adapters.EmployeeAdapter;
import com.john.realtimedb.ui.views.SplashView;
import com.john.realtimedb.view_model.EmployeeListHandler;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


public class ActivityEmployeeList extends BaseActivity implements SplashView {


    @Inject
    EmployeeListHandler handler;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO:
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        setUp();
    }


    @Override
    protected void setUp() {
        fabAddSetUp();
        recyclerViewSetUp();
        getCompositeDisposable().add(mergeEvent()
                .compose(handler.loadEvent())
                .subscribe(model -> {

                    swipeRefresh.setRefreshing(model.reloading);

                    progressBar.setVisibility(model.inProgress?View.VISIBLE:View.GONE);

                    adapter.setItemList(model.employeeList);

                    adapter.notifyDataSetChanged();

                    if (model.errorMessage!=null){
                        onError(model.errorMessage);
                    }


                }));

    }


    private void recyclerViewSetUp(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EmployeeAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void fabAddSetUp(){
        getCompositeDisposable().add(RxView.clicks(fabAdd)
                .subscribe(__ -> {
                    Intent intent = new Intent(this,ActivityAddEmployee.class);
                    startActivity(intent);
                }));
    }

    private Observable<EmployeeEvent> mergeEvent(){
        return Observable.merge(
                loadEvent(),
                listenEvent());
    }

    private Observable<LoadListEvent> loadEvent(){
        return RxSwipeRefreshLayout.refreshes(swipeRefresh)
                .map(__ -> LoadListEvent.reload())
                .startWith(LoadListEvent.load());
    }

    private Observable<ListListenEvent> listenEvent(){
        return Observable.just(new ListListenEvent());
    }




}
