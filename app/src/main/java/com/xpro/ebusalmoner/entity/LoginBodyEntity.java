package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/20.
 */
public class LoginBodyEntity {

    private LoginDriverEntity driver;

    public LoginDriverEntity getDriver() {
        return driver;
    }

    public void setDriver(LoginDriverEntity driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "LoginBodyEntity{" +
                "driver=" + driver +
                '}';
    }
}
