package com.john.realtimedb.utils;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;

public final class DialogUtils {

    private DialogUtils(){

    }

    /////////////////Dialog Manager/////////////////////////

    public static void removeDialogs(Activity context) {
        List<Fragment> fragments = ((FragmentActivity)context).getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof DialogFragment) {
                    ((DialogFragment) fragment).dismiss();
                }
            }
        }
    }

    public static void showDialog(Activity context, DialogFragment fragment, String tag){
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        removeDialogs(context);
        try {
            if(fragment!=null&&fragment.getDialog()!=null&&
                    fragment.getDialog().isShowing()){
                return;
            }
            fragment.show(fragmentManager, tag);
        }catch (Exception e){
            Log.e("myTag", "showDialog: "+e.toString() );
        }
    }
}
