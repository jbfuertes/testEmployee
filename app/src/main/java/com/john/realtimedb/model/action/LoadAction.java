package com.john.realtimedb.model.action;

/**
 * Created by john on 3/12/18.
 */

public final class LoadAction {
    public final boolean load;
    public final boolean reload;

    public LoadAction(boolean load, boolean reload) {
        this.load = load;
        this.reload = reload;
    }

}
