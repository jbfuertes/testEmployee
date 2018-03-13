package com.john.realtimedb.model.result;


/**
 * Created by john on 3/12/18.
 */

public final class RegisterEmployeeResult {

    public final boolean inProgress;
    public final String errorMessage;
    public final boolean success;

    private RegisterEmployeeResult(Builder builder) {

        this.inProgress = builder.inProgress;
        this.errorMessage = builder.errorMessage;
        this.success = builder.success;

    }

    public static final class Builder{

        private boolean inProgress;
        private String errorMessage;
        private boolean success;

        public RegisterEmployeeResult build(){
            return new RegisterEmployeeResult(this);
        }

        public Builder inProgress(boolean inProgress){
            this.inProgress = inProgress;
            return this;
        }

        public Builder withErrorMessage(String errorMessage){
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder success(boolean success){
            this.success = success;
            return this;
        }

    }
}
