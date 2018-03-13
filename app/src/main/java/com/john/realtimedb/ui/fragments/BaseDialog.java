package com.john.realtimedb.ui.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.john.realtimedb.di.component.ActivityComponent;
import com.john.realtimedb.ui.activities.BaseActivity;
import com.john.realtimedb.ui.views.DialogView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by john on 12/6/17.
 */

public abstract class BaseDialog extends DialogFragment implements DialogView {

    private BaseActivity activity;

    private CompositeDisposable compositeDisposable;

    protected abstract void setUp(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    /*@NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }
    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void dismissDialog(String tag) {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading() {
        if (activity != null) {
            activity.showLoading();
        }
    }

    @Override
    public void removeDialog() {
        dismiss();
    }

    @Override
    public void onError(String message) {
        if (activity != null) {
            activity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (activity != null) {
            activity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

}
