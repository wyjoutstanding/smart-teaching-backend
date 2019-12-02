package pers.wyj.smartteaching.webApi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import pers.wyj.smartteaching.common.AccountType;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.model.UserEntity;
import pers.wyj.smartteaching.service.UserService;

import javax.persistence.Entity;
import javax.persistence.PreUpdate;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 14:04
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/user/")
public class UserApi {
    @Autowired
    UserService userService;

    /**
     *  登录
     * @param userEntity 接受用户名和密码--json格式，没有的字段默认为null
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public WebApiResult login(@RequestBody UserEntity userEntity) {
        String accountName = userEntity.getAccountName();
        String accountPassword = userEntity.getAccountPassword();
        System.out.println("账户："+accountName+" 密码："+accountPassword);
        WebApiResult result = new WebApiResult(WebApiResultCode.PARAMETER_ERROR);
        if (userService.isExistedByAccountName(accountName)) { // 用户存在
            UserEntity realUserEntity = userService.getByAccountName(accountName);
            String realPassword = realUserEntity.getAccountPassword();
            if (accountPassword.equals(realPassword)) { // 密码相同
                result.setWebApiResult(WebApiResultCode.LOGIN_SUCCESS, realUserEntity);
            }
            else result.setWebApiResult(WebApiResultCode.LOGIN_PASSWORD_ERROR); // 密码错误
        }
        else { // 用户不存在
            result.setWebApiResult(WebApiResultCode.LOGIN_ACCOUNTNAME_UNEXISTED);
        }
        return result;
    }

    /**
     *  注册
     * @param accountName 账户名
     * @param accountPassword 密码
     * @param accountType 账户类型--0：学生，1：教师
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
//    public WebApiResult register(String accountName, String accountPassword, int accountType) {
    public WebApiResult register(@RequestBody UserEntity _userEntity) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.PARAMETER_ERROR);
        if (userService.isExistedByAccountName(_userEntity.getAccountName())) { // 账户已存在
            webApiResult.setWebApiResult(WebApiResultCode.REGISTER_ACCOUNTNAME_EXISTED);
        }
        else {
            UserEntity userEntity = new UserEntity();
            userEntity.setAccountName(_userEntity.getAccountName());
            userEntity.setAccountPassword(_userEntity.getAccountPassword());
            if (_userEntity.getAccountType().ordinal() == AccountType.STUDENT.ordinal()) { // 账户类型
                userService.addStudent(userEntity);
            }
            else userService.addTeacher(userEntity);
            webApiResult.setWebApiResult(WebApiResultCode.REGISTER_SUCCESS, userEntity);
        }
        return  webApiResult;
    }

    /**
     * 绑定个人信息（可以增加任意条）
     * @param userId 用户唯一id
     * @param userName 用户名
     * @param userGender 性别
     * @param userUniqueId 学号/工号
     * @param userMail 邮箱
     * @param userPhone 手机号
     * @return
     */
    @RequestMapping(value = "bindPersonInfo", method = RequestMethod.POST)
    public WebApiResult bindPersonInfo(@RequestBody UserEntity userEntity) {
        UserEntity oldUserEntity = userService.getByUserId(userEntity.getId()); // 先取出原信息
        // 必须判断，否则之前有的字段，在下一次别的字段修改时就会被清空
        if (userEntity.getUserName() != null)oldUserEntity.setUserName(userEntity.getUserName());
        if (userEntity.getUserGender() != null) oldUserEntity.setUserGender(userEntity.getUserGender());
        if (userEntity.getUserUniqueId() != null) oldUserEntity.setUserUniqueId(userEntity.getUserUniqueId());
        if (userEntity.getUserMail() != null) oldUserEntity.setUserMail(userEntity.getUserMail());
        if (userEntity.getUserPhone() != null) oldUserEntity.setUserPhone(userEntity.getUserPhone());
        userService.updateUserEntity(oldUserEntity);
        return new WebApiResult(WebApiResultCode.BIND_SUCCESS, oldUserEntity);
    }

    /**
     * 存储账户id、旧密码和新密码
     */
    @Getter
    static class Account {
        Long userId;
        String oldPassword;
        String newPassword;
        public Account(){}
        public Account(Long userId, String oldPassword, String newPassword) {
            this.userId = userId;
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }
    /**
     * 修改密码
     * @param userId 用户id
     * @param oldPassword 原有密码
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping(value = "editPassword", method = RequestMethod.POST)
    public WebApiResult editPassword(@RequestBody Account account) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        UserEntity userEntity = userService.getByUserId(account.getUserId());
        if (userEntity != null) {
            if (account.getOldPassword().equals(userEntity.getAccountPassword())) {
                userEntity.setAccountPassword(account.getNewPassword());
                userService.updateUserEntity(userEntity);
                webApiResult.setWebApiResult(WebApiResultCode.UPDATE_PASSWORD_SUCCESS);
            }
            else webApiResult.setWebApiResult(WebApiResultCode.UPDATE_PASSWORD_ERROR);
        }
        return webApiResult;
    }

    /**
     * 获取用户基本信息
     * @param userId 用户唯一id
     * @return
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public WebApiResult getUserInfo(Long userId) {
        UserEntity userEntity  = userService.getByUserId(userId);
        return new WebApiResult(WebApiResultCode.SUCCESS, userEntity);
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public WebApiResult test(@RequestBody UserEntity userEntity) {
        System.out.println(userEntity);
        return new WebApiResult(WebApiResultCode.SUCCESS, userEntity);
    }
}
