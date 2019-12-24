package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import pers.wyj.smartteaching.model.ClassStudentEntity;

import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:35
 * @Version 1.0
 */
public interface ClassStudentEntityDao extends JpaRepository<ClassStudentEntity, Long> {
    List<ClassStudentEntity> getAllByStudentId(Long studentId); // 根据学生id获取所有班级
    List<ClassStudentEntity> getAllByClassId(Long classId); // 根据班级id获取所有学生

    boolean existsByClassIdAndStudentId(Long classId, Long studentId); // 判断是否存在关系

    @Modifying
    void deleteByClassIdAndStudentId(Long classId, Long studentId); // 根据班级和学生id删除

    @Modifying
    void deleteAllByClassId(Long classId); // 根据班级id删除
}

