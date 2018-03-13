package com.john.realtimedb.model.ui_request;

/**
 * Created by john on 3/12/18.
 */

public final class CheckEvent extends RegisterEmployeeEvent {
    public final String firstName;
    public final String lastName;
    public final String email;

    public CheckEvent(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
