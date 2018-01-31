package com.chris.hqteach.bean;

/**
 * Created by chris on 17/6/1.
 */

public class TeachWebOrder implements OrderInterface {

    private int orderNum = 0;
    private String url = "";

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public OrderInterface transferFromJson(String json) {
        return null;
    }

    @Override
    public boolean execute() {
        return false;
    }
}
