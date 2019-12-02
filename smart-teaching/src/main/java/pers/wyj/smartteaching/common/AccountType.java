package pers.wyj.smartteaching.common;

import lombok.Getter;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 13:01
 * @Version 1.0
 */
@Getter
public enum  AccountType {
    STUDENT(100), // 等级权限
    TEACHER(200);

    private int level;
    AccountType(int level){
        this.level = level;
    }
}
