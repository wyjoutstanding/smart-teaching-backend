package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.QuestionEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 11:14
 * @Version 1.0
 */
public interface QuestionEntityDao extends JpaRepository<QuestionEntity, Long> {
}
