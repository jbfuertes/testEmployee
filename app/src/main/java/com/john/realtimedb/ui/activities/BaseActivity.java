package com.john.realtimedb.ui.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.john.realtimedb.App;
import com.john.realtimedb.di.component.ActivityComponent;
import com.john.realtimedb.di.component.DaggerActivityComponent;
import com.john.realtimedb.di.module.ActivityModule;
import com.john.realtimedb.ui.fragments.BaseFragment;
import com.john.realtimedb.ui.views.BaseView;
import com.john.realtimedb.utils.DialogUtils;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity
        implements BaseView, BaseFragment.Callback{

    protected abstract void setUp();

    private ActivityComponent activityComponent;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App)getApplication()).getAppComponent())
                .build();

        compositeDisposable = new CompositeDisposable();

    }


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Confirm Exit");
        alertDialog.setMessage("Close App?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> finish());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void showLoading() {
  //TODO: add loading
        /*DialogLoading dialogLoading = new DialogLoading();
        dialogLoading.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogThemeNoBackGround);
        DialogUtils.showDialog(this,dialogLoading,"dialogLoading");
*/
    }

    @Override
    public void removeDialog() {
        DialogUtils.removeDialogs(this);
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void onError(String message) {
        showSnackBar(message);
        //TODO: add error
       /* DialogFailed dialogFailed = new DialogFailed();
        dialogFailed.setTitle(message);
        dialogFailed.setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogThemeFailed);
        DialogUtils.showDialog(this,dialogFailed,"dialogFailed");*/
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void setFragment(Fragment fragment, String tag){
        //TODO add fragment settings
        /*getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.viewContainer, fragment,tag)
                .commit();*/
    }

    public void setFragment(Fragment fragment){

        //TODO: add fragment settings
        /* getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .replace(R.id.viewContainer, fragment)
                .commit();*/
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }


}
