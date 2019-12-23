package pers.wyj.smartteaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wyj.smartteaching.dao.ClassHomeworkEntityDao;
import pers.wyj.smartteaching.dao.HomeworkEntityDao;
import pers.wyj.smartteaching.model.HomeworkEntity;

import java.util.Date;
import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/22 18:25
 * @Version 1.0
 */
@Service
@Transactional
public class HomeworkService {
    @Autowired
    HomeworkEntityDao homeworkEntityDao;
    @Autowired
    ClassHomeworkEntityDao classHomeworkEntityDao;

    public void createHomework(HomeworkEntity homeworkEntity) {
        Date date = new Date();
        homeworkEntity.setHomeworkCreateTime(date); // 创建时间
        if (homeworkEntity.getFileId() == null) homeworkEntity.setFileId(0L);
        if (homeworkEntity.getHomeworkFormat() == null) homeworkEntity.setHomeworkFormat("任意");
        homeworkEntityDao.save(homeworkEntity);
    }
    public void deleteAllByHomeworkId(Long homeworkId) {
        classHomeworkEntityDao.deleteAllByHomeworkId(homeworkId);
    }

    public void deleteAllByClassId(Long classId) {
        classHomeworkEntityDao.deleteAllByClassId(classId);
    }
}
