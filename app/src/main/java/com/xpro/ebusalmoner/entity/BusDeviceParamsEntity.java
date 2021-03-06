package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/28.
 */
public class BusDeviceParamsEntity {
    private String busCode;
    private String type;
    private String idCode;

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    @Override
    public String toString() {
        return "BusDeviceParamsEntity{" +
                "busCode='" + busCode + '\'' +
                ", type='" + type + '\'' +
                ", idCode='" + idCode + '\'' +
                '}';
    }
}
