package pers.wyj.smartteaching.common;

import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 14:10
 * @Version 1.0
 */
@Getter
@Setter
public class WebApiResult { // web请求时返回的结果
    private int code; // 返回码
    private String message; // 返回消息
    private Object data; // 返回数据
    public WebApiResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public WebApiResult(WebApiResultCode webApiResultCode, Object data) {
        this.code = webApiResultCode.getCode();
        this.message = webApiResultCode.getMsg();
        this.data = data;
    }
    public WebApiResult(WebApiResultCode webApiResultCode) {
        this.code = webApiResultCode.getCode();
        this.message = webApiResultCode.getMsg();
        this.data = null;
    }
    public void setWebApiResult(WebApiResultCode webApiResultCode) {
        this.code = webApiResultCode.getCode();
        this.message = webApiResultCode.getMsg();
    }
    public void setWebApiResult(WebApiResultCode webApiResultCode, Object data) {
        this.code = webApiResultCode.getCode();
        this.message = webApiResultCode.getMsg();
        this.data = data;
    }
}
