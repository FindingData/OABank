package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 项目列表状态枚举
 */
public enum ProjectCenterListType {
    Todo(40001006),//待派单
    Doing(40001001),//进行中
    Pause(40001005),//暂停
    Stop(40001003);//已终止
    private final int type;

    ProjectCenterListType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
