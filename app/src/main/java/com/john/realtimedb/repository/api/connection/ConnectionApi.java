package com.john.realtimedb.repository.api.connection;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by john on 3/12/18.
 */

@Singleton
public class ConnectionApi implements ConnectionManager{

    @Inject
    public ConnectionApi() {
    }


    @Override
    public Observable<Boolean> listen() {
        return ReactiveNetwork.observeInternetConnectivity();
    }

    @Override
    public Single<Boolean> checkStatus() {
        return ReactiveNetwork.checkInternetConnectivity();
    }
}
