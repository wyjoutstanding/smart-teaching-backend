package pers.wyj.smartteaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wyj.smartteaching.dao.ClassesEntityDao;
import pers.wyj.smartteaching.model.ClassesEntity;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/9 19:31
 * @Version 1.0
 */
@Service
public class ClassesService {
    @Autowired
    ClassesEntityDao classesEntityDao;

    /**
     * 根据班级id生成唯一的邀请码
     * @param classId
     * @return
     */
    private String getInviteCode(Long classId) {
        String inviteCode = classId.toString();
        inviteCode = String.format("%010s", inviteCode);
        return inviteCode;
    }
    /**
     * 添加班级
     * @param classesEntity
     */
    public void addClass(ClassesEntity classesEntity) {
        Date date = new Date();
        classesEntity.setClassCreateTime(date); // 生成日期
        classesEntity.setClassInviteCode(Long.toString(date.getTime())); // 根据日期生成邀请码
        classesEntity.setClassStatus(1L); // 班级状态1：表示活跃 ；0：表示删除
        classesEntityDao.save(classesEntity); // 保存
    }

    /**
     * 根据班级id获取班级
     * @param classId
     * @return
     */
    public ClassesEntity getClassById(Long classId) {
        return classesEntityDao.getById(classId);
    }

    /**
     * 根据教师id获取班级
     * @param teacherId
     * @return 班级列表
     */
    public List<ClassesEntity> getClassByTeacherId(Long teacherId) {
        return classesEntityDao.getByTeacherId(teacherId);
    }

    /**
     * 根据班级邀请码获取班级
     * @param inviteCode
     * @return
     */
    public ClassesEntity getClassByInviteCode(String inviteCode) {
        return classesEntityDao.getByClassInviteCode(inviteCode);
    }

    /**
     * 根据教师id和班级名查询
     * @param teacherId
     * @param className
     * @return
     */
    public ClassesEntity getClassByTeacherIdAndClassName(Long teacherId, String className) {
        return classesEntityDao.getByTeacherIdAndClassName(teacherId, className);
    }

    /**
     * 删除班级
     * @param classId
     */
    public void deleteClassByClassId(Long classId) {
        classesEntityDao.deleteById(classId);
    }
}
