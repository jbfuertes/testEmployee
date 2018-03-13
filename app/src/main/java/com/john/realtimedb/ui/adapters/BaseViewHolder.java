package com.john.realtimedb.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by john on 12/3/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int currentPosition;
    private CompositeDisposable compositeDisposable;

    BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position){
        compositeDisposable = new CompositeDisposable();
        currentPosition = position;
        clear();
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public CompositeDisposable getCompositeDisposable(){
        return compositeDisposable;
    }

}
