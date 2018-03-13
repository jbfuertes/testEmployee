package com.john.realtimedb.ui.views;

import android.support.annotation.StringRes;

/**
 * Created by john on 11/28/17.
 */

public interface BaseView {

    void showLoading();

    void removeDialog();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();


}
