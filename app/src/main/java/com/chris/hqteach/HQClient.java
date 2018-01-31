package com.chris.hqteach;

/**
 * Created on 17/4/2.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail : 标识电子班牌的相关信息,后续可追加
 */

public class HQClient {

    //设备ID
    private int ID = 0;
    //班级ID
    private int GROUP_ID = 0;
    //学校ID
    private int SCHOOLE_ID = 0;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(int GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public int getSCHOOLE_ID() {
        return SCHOOLE_ID;
    }

    public void setSCHOOLE_ID(int SCHOOLE_ID) {
        this.SCHOOLE_ID = SCHOOLE_ID;
    }
}
