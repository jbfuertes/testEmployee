package com.john.realtimedb.model.ui_request;

/**
 * Created by john on 3/12/18.
 */

public final class LoadListEvent extends EmployeeEvent {
    public final boolean load;
    public final boolean reload;

    private LoadListEvent(boolean load, boolean reload) {
        this.load = load;
        this.reload = reload;
    }

    public static LoadListEvent load(){
        return new LoadListEvent(true,false);
    }

    public static LoadListEvent reload(){
        return new LoadListEvent(false,true);
    }
}
