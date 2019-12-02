package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.ClassStudentEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:35
 * @Version 1.0
 */
public interface ClassStudentEntityDao extends JpaRepository<ClassStudentEntity, Integer> {
}
