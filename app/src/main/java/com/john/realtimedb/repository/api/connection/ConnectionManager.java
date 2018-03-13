package com.john.realtimedb.repository.api.connection;


import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by john on 3/12/18.
 */

public interface ConnectionManager {
    Observable<Boolean> listen();

    Single<Boolean> checkStatus();
}
