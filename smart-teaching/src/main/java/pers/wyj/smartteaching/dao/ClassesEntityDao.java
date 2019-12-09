package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.ClassesEntity;

import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:31
 * @Version 1.0
 */
// JpaRepository<T, ID>中的ID必须和实体中主键类型一致
public interface ClassesEntityDao extends JpaRepository<ClassesEntity, Long> {

    ClassesEntity getById(Long id); // 按照命名规则，写接口就行
    ClassesEntity getByClassInviteCode(String classInviteCode); // 按照命名规则，写接口就行
    List<ClassesEntity> getByTeacherId(Long teacherId); // 根据教师id获取班级列表
    ClassesEntity getByClassName(String className); // 根据班级名获取班级

    ClassesEntity getByTeacherIdAndClassName(Long teacherId, String className); // 根据教师名和班级名查询

    boolean existsByTeacherIdAndId(Long teacherId, Long id); // 根据教师id和班级id查询是否存在
}
