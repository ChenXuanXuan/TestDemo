package com.hjhz.testdemo.entity;

/**
 * Created by yt on 2015/7/20.
 */
public class ChannalBean extends Entity {

    String columnName;
    int type;
    int order;
    int lixian;

    int status ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLixian() {
        return lixian;
    }

    public void setLixian(int lixian) {
        this.lixian = lixian;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
