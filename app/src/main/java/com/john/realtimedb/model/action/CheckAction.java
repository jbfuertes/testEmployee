package com.john.realtimedb.model.action;

/**
 * Created by john on 3/12/18.
 */

public final class CheckAction {

    public final String firstName;
    public final String lastName;
    public final String email;

    public CheckAction(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
