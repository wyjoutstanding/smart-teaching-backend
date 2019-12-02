package pers.wyj.smartteaching.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 12:35
 * @Version 1.0
 */
@Getter
public enum AccountStatus {
//    NOT_ACTIVE("未激活"),//未激活(未填写学校学号等信息)
    NORMAL("正常"),//账号正常
    FREEZE("冻结"),//账号冻结
    BAN("封禁"),//账号封禁
    DELETE("注销"),//账号删除
    ;
    AccountStatus(String msg) {
        this.msg = msg;
    }
    private String msg;
}
