package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.QuestionBankEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 11:13
 * @Version 1.0
 */
public interface QuestionBankEntityDao extends JpaRepository<QuestionBankEntity, Long> {
}
