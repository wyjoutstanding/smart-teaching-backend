package pers.wyj.smartteaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wyj.smartteaching.common.AccountStatus;
import pers.wyj.smartteaching.common.AccountType;
import pers.wyj.smartteaching.dao.UserEntityDao;
import pers.wyj.smartteaching.model.UserEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 13:13
 * @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    UserEntityDao userEntityDao;

    /**
     * 添加用户事务
     * @param userEntity 用户实体
     * @return 用户实体
     */
    @Transactional
    public UserEntity addUser(UserEntity userEntity) {
        return userEntityDao.save(userEntity);
    }

    /**
     * 添加教师
     * @param userEntity
     * @return
     */
    public UserEntity addTeacher(UserEntity userEntity) {
        userEntity.setAccountStatus(AccountStatus.NORMAL);
        userEntity.setAccountType(AccountType.TEACHER);
        return this.addUser(userEntity);
    }

    /**
     * 添加学生
     * @param userEntity
     * @return
     */
    public UserEntity addStudent(UserEntity userEntity) {
        userEntity.setAccountStatus(AccountStatus.NORMAL);
        userEntity.setAccountType(AccountType.STUDENT);
        return this.addUser(userEntity);
    }

    /**
     * 根据用户id查询是否存在
     * @param userId
     * @return
     */
    public boolean isExistedByUserId(Long userId) {
        return userEntityDao.existsById(userId);
    }

    /**
     * 根据账户名查询用户是否存在
     * @param accountName
     * @return
     */
    public boolean isExistedByAccountName(String accountName) {
        return userEntityDao.existsByAccountName(accountName);
    }

    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    public UserEntity getByUserId(Long userId) {
        return userEntityDao.getById(userId);
    }

    /**
     * 根据账户名获取用户
     * @param accountName
     * @return
     */
    public UserEntity getByAccountName(String accountName) {
        return userEntityDao.getByAccountName(accountName);
    }

    /**
     * 更新用户实体（修改）
     * @param userEntity
     */
    public void updateUserEntity(UserEntity userEntity) {
        userEntityDao.save(userEntity);
    }
}
