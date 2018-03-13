package com.john.realtimedb.utils;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public final class KeyboardUtils {

    private KeyboardUtils(){

    }


    ///////////////////KeyBoard Manager///////////////////

    public static void hideSoftKey(View view,Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e){
            Log.d("helper", "hideSoftKey: "+e.toString());
        }
    }

    public static void hideSoftKey(Activity activity) {
        try {
            if(activity.getCurrentFocus()!=null){
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception e){
            Log.e("myTag", "hideSoftKey: "+e.toString() );
        }
    }

    public static void showSoftKey(View view,Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void editTextOutsideTapListener(View[] view,Activity activity){
        for(View layoutView:view){
            layoutView.setOnTouchListener(new View.OnTouchListener() {
                long startClickTime;
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        startClickTime = System.currentTimeMillis();

                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                        if (System.currentTimeMillis() - startClickTime < ViewConfiguration.getTapTimeout()) {

                            try {
                                if(activity.getCurrentFocus()!=null&&activity.getCurrentFocus() instanceof EditText){
                                    hideSoftKey(activity.getCurrentFocus(),activity);
                                }
                            }catch (Exception e){
                                Log.e("myTag", "onTouch: "+e.toString() );
                            }

                        } else {

                            // Touch was a not a simple tap.

                        }

                    }
                    return true;
                }
            });
        }


    }

}
