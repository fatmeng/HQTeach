package com.chris.hqteach.bean;

import android.content.Context;
import android.os.Build;

import com.chris.hqteach.utils.SystemUtils;

/**
 * Created by chris on 17/6/1.
 * 电子班牌相关信息
 */

public class Client {

    /**
     * 学校
     */
    private String school = "";
    /**
     * 年级
     */
    private String gradle = "";
    /**
     * 斑级,也就是几班 class是关键字,不可用
     */
    private String cls = "";

    /**
     * mac
     */
    private String mac = "";
    /**
     * serial号
     */
    private String serialNum = "";

    /**
     * 有事找孙康，哈哈。我不知道这几个参数干啥用
     */
    private String key = "";
    private int orderId = 0;
    private long timestamp = System.currentTimeMillis();
    private String url;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGradle() {
        return gradle;
    }

    public void setGradle(String gradle) {
        this.gradle = gradle;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class Builder{
        private Client client = null;

        public Builder(){
            client = new Client();
        }

        public Builder setSchool(String school){
            client.setSchool(school);
            return this;
        }
        public Builder setGradle(String gradle){
            client.setGradle(gradle);
            return this;
        }
        public Builder setCls(String cls){
            client.setCls(cls);
            return this;
        }
        public Builder setMac(String mac){
            client.mac = mac;
            return this;
        }
        public Builder setSerialNum(String serailNum){
            client.serialNum = serailNum;
            return this;
        }
        public Builder setKey(String key){
            client.key = key;
            return this;
        }
        public Builder setOrderId(int orderId){
            client.orderId = orderId;
            return this;
        }
        public Builder setTimeStamp(long timeStamp){
            client.timestamp = timeStamp;
            return this;
        }
        public Builder setUrl(String url){
            client.url = url;
            return this;
        }


        public Client build(){
            return client;
        }

    }


    /**
     * 测试用方法,给提供默认电子班牌信息
     * @param mContext
     * @return
     */
    public static Client defaultInfo(Context mContext) {
        Builder builder = new Builder();
        Client client = builder.setSchool("春田花花幼儿园")
                .setGradle("大幼")
                .setCls("一班")
                .setMac(SystemUtils.getMac(mContext))
                .setSerialNum(Build.SERIAL)
                .setKey("client_bind")
                .setOrderId(0)
                .setTimeStamp(System.currentTimeMillis())
                .setUrl("").build();
        return client;
    }
}
