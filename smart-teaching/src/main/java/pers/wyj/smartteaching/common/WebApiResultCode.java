package pers.wyj.smartteaching.common;

import lombok.Getter;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 14:13
 * @Version 1.0
 */
@Getter
public enum WebApiResultCode {
    SUCCESS("成功请求",200),
    UN_LOGIN("未登录",300),
    LOGIN_ACCOUNTNAME_UNEXISTED("账户名不存在",301),
    LOGIN_PASSWORD_ERROR("登录密码错误",303),
    LOGIN_SUCCESS("登录成功",304),
    REGISTER_ACCOUNTNAME_EXISTED("账户名已存在",310),
    REGISTER_SUCCESS("注册成功",311),
    BIND_SUCCESS("绑定个人信息成功",320),
    UPDATE_PASSWORD_SUCCESS("修改密码成功",330),
    UPDATE_PASSWORD_ERROR("原有密码错误",331),
    CLASS_NAME_EXISTED("班级名已存在", 337),
    CLASS_CREATE_SUCCESS("班级创建成功", 338),
    CLASS_DELETE_SUCCESS("班级删除成功", 340),
    NO_AUTH("无权限访问",400),
    FAILURE("系统内部错误",500),
    PARAMETER_ERROR("请求参数错误",600),
    REQUEST_METHOD_NOT_SUPPORT("HTTP请求方式错误,不支持的请求方式",700);

    private int code;
    private String msg;
    private WebApiResultCode(String _msg,int _code)
    {
        this.msg = _msg;
        this.code = _code;
    }
    @Override
    public String toString(){
            return this.msg;
        }
}
