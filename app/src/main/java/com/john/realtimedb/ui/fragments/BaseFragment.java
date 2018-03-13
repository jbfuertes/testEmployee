package com.john.realtimedb.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.john.realtimedb.di.component.ActivityComponent;
import com.john.realtimedb.ui.activities.BaseActivity;
import com.john.realtimedb.ui.views.BaseView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by john on 12/4/17.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private BaseActivity activity;

    private CompositeDisposable compositeDisposable;

    protected abstract void setUp(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
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
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void showLoading() {
        if(activity!=null){
            activity.showLoading();
        }
    }

    @Override
    public void removeDialog() {
        if (activity != null) {
            activity.removeDialog();
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
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
    public boolean isNetworkConnected() {
        return false;
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
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void setFragment(Fragment fragment, String tag){
        //TODO: add fragment settings

        /*getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.viewContainer, fragment,tag)
                .commit();*/

    }

    public void setFragment(Fragment fragment){
        //TODO: add fragment settings
        /*getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.viewContainer, fragment)
                .commit();*/
    }

    /*public void addGoogleMap(int containter){
        getChildFragmentManager()
                .beginTransaction()
                .add(containter, mapFragment)
                .commit();
    }*/

    /*public SupportMapFragment getMapFragment(){
        return mapFragment;
    }*/

    public void setFragment(String tag) throws NullPointerException {
        Fragment fragment = getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag(tag);
        if (fragment!=null){
            setFragment(fragment,tag);
        }else {
            throw new NullPointerException("no fragment found");
        }
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);


    }

}
