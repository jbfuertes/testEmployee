package com.john.realtimedb.model.result;

import java.util.Collections;
import java.util.List;

/**
 * Created by john on 3/12/18.
 */

public final class EmployeeResult {

    public final boolean inProgress;
    public final boolean reloading;
    public final boolean success;
    public final String errorMessage;
    public final List<Employee> employeeList;

    private EmployeeResult(Builder builder){
        this.inProgress = builder.inProgress;
        this.reloading = builder.reloading;
        this.success = builder.success;
        this.errorMessage = builder.errorMessage;
        this.employeeList = Collections.unmodifiableList(builder.employeeList);
    }

    public static final class Builder{
        private boolean inProgress;
        private boolean reloading;
        private boolean success;
        private String errorMessage;
        private List<Employee> employeeList;

        public EmployeeResult build(){
            return new EmployeeResult(this);
        }

        public Builder inProgress(boolean inProgress){
            this.inProgress = inProgress;
            return this;
        }

        public Builder reloading(boolean reloading){
            this.reloading = reloading;
            return this;
        }

        public Builder success(boolean success){
            this.success = success;
            return this;
        }

        public Builder withErrorMessage(String errorMessage){
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder withEmployeeList(List<Employee> employeeList){
            this.employeeList = employeeList;
            return this;
        }

    }

    public static final class Employee{
        public final String firstName;
        public final String lastName;
        public final String email;

        private Employee() {
            this.firstName = null;
            this.lastName = null;
            this.email = null;
        }

        private Employee(Builder builder){
            this.firstName = builder.firstName;
            this.lastName = builder.lastName;
            this.email = builder.email;
        }

        public static final class Builder{
            private String firstName;
            private String lastName;
            private String email;

            public Employee build(){
                return new Employee(this);
            }

            public Builder withFirstName(String firstName){
                this.firstName = firstName;
                return this;
            }

            public Builder withLastName(String lastName){
                this.lastName = lastName;
                return this;
            }

            public Builder withEmail(String email){
                this.email = email;
                return this;
            }

        }

    }


}
