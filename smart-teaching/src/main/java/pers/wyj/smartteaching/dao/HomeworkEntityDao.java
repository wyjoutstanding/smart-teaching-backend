package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.HomeworkEntity;

import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 11:12
 * @Version 1.0
 */
public interface HomeworkEntityDao extends JpaRepository<HomeworkEntity, Long> {

    public HomeworkEntity getById(Long id); // 根据id获取作业

    public List<HomeworkEntity> getAllByTeacherId(Long teacherId); // 根据教师id获取所有作业

    public boolean existsByTeacherIdAndId(Long teacherId, Long id); // 根据教师id和作业id判断是否存在

//    public boolean existsByTeacherIdAndId
}
