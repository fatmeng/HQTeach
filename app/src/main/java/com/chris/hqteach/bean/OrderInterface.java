package com.chris.hqteach.bean;

/**
 * Created by chris on 17/6/1.
 * 用于解析服务器下发的命令的接口
 */

public interface OrderInterface {
    /**
     * 将接收到的jsonString转换成OrderInterface的子类,orderBean对象
     * @param json
     * @return
     */
    OrderInterface transferFromJson(String json);

    /**
     * 执行该Order指令
     * @return 如果执行成功返回true;如果执行失败,返回false;
     */
    boolean execute();

}
