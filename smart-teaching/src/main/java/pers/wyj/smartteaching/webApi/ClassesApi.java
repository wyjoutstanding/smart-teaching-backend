package pers.wyj.smartteaching.webApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wyj.smartteaching.common.AccountType;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.dao.ClassStudentEntityDao;
import pers.wyj.smartteaching.dao.ClassesEntityDao;
import pers.wyj.smartteaching.dao.UserEntityDao;
import pers.wyj.smartteaching.model.ClassStudentEntity;
import pers.wyj.smartteaching.model.ClassesEntity;
import pers.wyj.smartteaching.model.UserEntity;
import pers.wyj.smartteaching.service.ClassesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/9 17:26
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/api/classes/")
public class ClassesApi {

    @Autowired
    ClassesService classesService;
    @Autowired
    ClassesEntityDao classesEntityDao;
    @Autowired
    ClassStudentEntityDao classStudentEntityDao;
    @Autowired
    UserEntityDao userEntityDao;
    /**
     * 教师创建班级
     * @param classesEntity 班级信息
     * @return
     */
    @RequestMapping(value = "createClass", method = RequestMethod.POST)
    public WebApiResult createClass(@RequestBody ClassesEntity classesEntity) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        ClassesEntity classesEntity1 = classesService.getClassByTeacherIdAndClassName(classesEntity.getTeacherId(), classesEntity.getClassName());
        if (classesEntity1 != null) {
            webApiResult.setWebApiResult(WebApiResultCode.CLASS_NAME_EXISTED);
        }
        else {
            classesService.addClass(classesEntity);
            webApiResult.setWebApiResult(WebApiResultCode.CLASS_CREATE_SUCCESS);
        }
        return webApiResult;
    }

    /**
     * 根据用户id获取所创建/加入的班级
     * @param id 用户id（教师/学生）
     * @return
     */
    @RequestMapping(value = "getClasses", method = RequestMethod.GET)
    public WebApiResult getClasses(Long id) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        UserEntity userEntity = userEntityDao.getById(id);
        List<ClassesEntity> classesEntityList = new ArrayList<>();
        if (userEntity != null) { // 用户存在
            if (userEntity.getAccountType() == AccountType.TEACHER) { // 教师
                classesEntityList = classesService.getClassByTeacherId(id);
            }
            else { // 学生
                List<ClassStudentEntity> classStudentEntityList = classStudentEntityDao.getAllByStudentId(id); // 获取该学生加入的所有班级
                for (ClassStudentEntity classStudentEntity : classStudentEntityList) { // 提取班级
                    classesEntityList.add(classesService.getClassById(classStudentEntity.getClassId()));
                }
            }
            webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, classesEntityList);
        }
        return webApiResult;
    }

    /**
     * 教师删除班级
     * @param teacherId 教师id
     * @param classId 班级id
     * @return
     */
    @RequestMapping(value = "deleteClass", method = RequestMethod.GET)
    public WebApiResult deleteClass(Long teacherId, Long classId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (classesEntityDao.existsByTeacherIdAndId(teacherId, classId)) {
            classesService.deleteClassByClassId(classId);
            webApiResult.setWebApiResult(WebApiResultCode.CLASS_DELETE_SUCCESS);
        }
        return webApiResult;
    }

    /**
     * 修改班级
     * @param classesEntity
     * @return
     */
    @RequestMapping(value = "modifyClass", method = RequestMethod.POST)
    public WebApiResult modifyClass(@RequestBody ClassesEntity classesEntity) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        ClassesEntity classesEntity1 = classesService.getClassById(classesEntity.getId());
        if (classesEntity1 != null && classesEntity1.getTeacherId() == classesEntity.getTeacherId()) { // 教师确实创建此班级
            if (classesEntity.getClassName() != null && !classesEntity.getClassName().equals("")) classesEntity1.setClassName(classesEntity.getClassName());
            if (classesEntity.getClassType() != null && !classesEntity.getClassType().equals("")) classesEntity1.setClassType(classesEntity.getClassType());
            classesService.updateClass(classesEntity1);
            webApiResult.setWebApiResult(WebApiResultCode.CLASS_EDIT_SUCCESS);
        }
        return webApiResult;
    }
    /**
     * 学生加入班级
     * @param studentId 学生唯一id
     * @param invitedCode 班级邀请码
     * @return
     */
    @RequestMapping(value = "joinClass", method = RequestMethod.GET)
    public WebApiResult joinClass(Long studentId, String classInviteCode) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.CLASS_INVITED_ERROR);
        ClassesEntity classesEntity = classesService.getClassByInviteCode(classInviteCode);
        if (classesEntity != null) { // 班级存在
            if (classStudentEntityDao.existsByClassIdAndStudentId(classesEntity.getId(), studentId)) { // 已加入
                webApiResult.setWebApiResult(WebApiResultCode.CLASS_ALREADY_JOIN);
            }
            else { // 尚未加入
                ClassStudentEntity classStudentEntity = new ClassStudentEntity();
                classStudentEntity.setClassId(classesEntity.getId());
                classStudentEntity.setStudentId(studentId);
                classStudentEntity.setJoinTime(new Date());
                classStudentEntityDao.save(classStudentEntity);
                webApiResult.setWebApiResult(WebApiResultCode.CLASS_JOIN_SUCCESS);
            }
        }
        return webApiResult;

    }

    /**
     * 学生退出班级
     * @param studentId 学生唯一id
     * @param classId 班级唯一id
     * @return
     */
    @RequestMapping(value = "quitClass", method = RequestMethod.GET)
    public WebApiResult quitClass(Long studentId, Long classId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (classStudentEntityDao.existsByClassIdAndStudentId(classId, studentId)) { // 存在
            classesService.quitClass(classId, studentId);
            webApiResult.setWebApiResult(WebApiResultCode.CLASS_QUIT_SUCCESS);
        }
        return webApiResult;
    }
}
