package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/26.
 */
public class LoginParmsEntity {
    private String simInfo;
    private String series;

    public String getSimInfo() {
        return simInfo;
    }

    public void setSimInfo(String simInfo) {
        this.simInfo = simInfo;
    }

    public String getSerial() {
        return series;
    }

    public void setSerial(String series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "LoginParmsEntity{" +
                "simInfo='" + simInfo + '\'' +
                ", serial='" + series + '\'' +
                '}';
    }
}
