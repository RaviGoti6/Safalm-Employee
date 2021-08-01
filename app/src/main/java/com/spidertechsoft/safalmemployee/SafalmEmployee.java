package com.spidertechsoft.safalmemployee;

import android.app.Application;

public class SafalmEmployee extends Application {

    private String empId;

    public String getempId() {
        return empId;
    }

    public void setempId(String empId) {
        this.empId = empId;
    }
}
