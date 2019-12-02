package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.UserEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 11:07
 * @Version 1.0
 */
public interface UserEntityDao extends JpaRepository<UserEntity, Long> {

    UserEntity getById(Long id);

    UserEntity getByAccountName(String accountName);

    boolean existsByAccountName(String accountName); // 根据账户名判断是否存在
}
