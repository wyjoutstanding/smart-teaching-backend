package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.HomeworkEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 11:12
 * @Version 1.0
 */
public interface HomeworkEntityDao extends JpaRepository<HomeworkEntity, Long> {

}
