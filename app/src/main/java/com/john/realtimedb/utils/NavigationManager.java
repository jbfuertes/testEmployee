package com.john.realtimedb.utils;


import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public final class NavigationManager {

    @SuppressLint("RestrictedApi")
    public static void btmNavBarReflection(BottomNavigationView bottomNavigationView){
        Field f = null;
        try {
            f = bottomNavigationView.getClass().getDeclaredField("mMenuView");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        BottomNavigationMenuView menuView=null;
        try {
            menuView = (BottomNavigationMenuView) f.get(bottomNavigationView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//  get the private BottomNavigationItemView[]  field
        try {
            f=menuView.getClass().getDeclaredField("mButtons");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        BottomNavigationItemView[] mButtons=null;
        try {
            mButtons = (BottomNavigationItemView[]) f.get(menuView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        for(int i=0;i<mButtons.length;i++){
            mButtons[i].setShiftingMode(false);
            mButtons[i].setChecked(true);
        }

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);

                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void resizingListener(View observeOn, View observer){
        observeOn.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            Rect r = new Rect();
            observeOn.getWindowVisibleDisplayFrame(r);
            int screenHeight = observeOn.getRootView().getHeight();

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                // keyboard is opened
                observer.setVisibility(View.GONE);
            }
            else {
                observer.post(()->observer.setVisibility(View.VISIBLE));
                //handler.Post(() -> observer.setVisibility(View.VISIBLE));
                // keyboard is closed
            }
        });
    }

}
