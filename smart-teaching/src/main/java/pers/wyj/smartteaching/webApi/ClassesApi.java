package pers.wyj.smartteaching.webApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.dao.ClassesEntityDao;
import pers.wyj.smartteaching.model.ClassesEntity;
import pers.wyj.smartteaching.service.ClassesService;

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
     * 根据教师id获取所创建的班级
     * @param teacherId 教师唯一id
     * @return
     */
    @RequestMapping(value = "getClasses", method = RequestMethod.GET)
    public WebApiResult getClasses(Long teacherId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, classesService.getClassByTeacherId(teacherId));
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
     * 学生加入班级
     * @param studentId 学生唯一id
     * @param invitedCode 班级邀请码
     * @return
     */
    @RequestMapping(value = "joinClass", method = RequestMethod.GET)
    public WebApiResult joinClass(Long studentId, String invitedCode) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
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
        return webApiResult;
    }
}
