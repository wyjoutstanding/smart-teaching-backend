package pers.wyj.smartteaching.webApi;

import com.sun.xml.bind.v2.TODO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pers.wyj.smartteaching.common.AccountType;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.dao.ClassHomeworkEntityDao;
import pers.wyj.smartteaching.dao.ClassStudentEntityDao;
import pers.wyj.smartteaching.dao.ClassesEntityDao;
import pers.wyj.smartteaching.dao.HomeworkEntityDao;
import pers.wyj.smartteaching.model.*;
import pers.wyj.smartteaching.service.ClassesService;
import pers.wyj.smartteaching.service.HomeworkService;
import pers.wyj.smartteaching.service.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/22 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/homework/")
@CrossOrigin
public class HomeworkApi {
    @Autowired
    UserService userService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkEntityDao homeworkEntityDao;
    @Autowired
    ClassesEntityDao classesEntityDao;
    @Autowired
    ClassHomeworkEntityDao classHomeworkEntityDao;
    @Autowired
    ClassesService classesService;
    @Autowired
    ClassStudentEntityDao classStudentEntityDao;
    /**
     * 创建作业
     * @param homeworkEntity 作业信息
     * @return
     */
    @PostMapping(value = "createHomework")
    public WebApiResult createHomework(@RequestBody HomeworkEntity homeworkEntity) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (userService.isExistedByUserId(homeworkEntity.getTeacherId())) {
            // 教师账户
            if (userService.getByUserId(homeworkEntity.getTeacherId()).getAccountType() == AccountType.TEACHER) {
                homeworkService.createHomework(homeworkEntity);
                webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
            }
        }

        return webApiResult;
    }

    /**
     * 发布作业
     * @param classHomeworkEntity 包含可见班级id，截止时间，作业id
     * @param teacherId
     * @return
     */
    @PostMapping(value = "postHomework")
    public WebApiResult postHomework(@RequestBody ClassHomeworkEntity classHomeworkEntity, @Param("teacherId") Long teacherId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        // 教师有该班级和作业 && 作业未有对应班级尚未有该记录
        if (homeworkEntityDao.existsByTeacherIdAndId(teacherId, classHomeworkEntity.getHomeworkId())
        && classesEntityDao.existsByTeacherIdAndId(teacherId, classHomeworkEntity.getClassId())) {
            classHomeworkEntity.setHomeworkPostTime(new Date());
            classHomeworkEntityDao.save(classHomeworkEntity);
            webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
        }
        return webApiResult;
    }
    /**
     * 修改作业信息（例如作业名称，有效时间，描述）
     * @param homeworkEntity
     * @return
     */
    @PostMapping(value = "modifyHomework")
    public WebApiResult modifyHomework(@RequestBody HomeworkEntity homeworkEntity) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (userService.isExistedByUserId(homeworkEntity.getTeacherId())) {
            if (userService.getByUserId(homeworkEntity.getTeacherId()).getAccountType() == AccountType.TEACHER) {
                HomeworkEntity homeworkEntity1 = homeworkEntityDao.getById(homeworkEntity.getId());
                if (homeworkEntity.getHomeworkTitle() != null && !"".equals(homeworkEntity.getHomeworkTitle()))
                    homeworkEntity1.setHomeworkTitle(homeworkEntity.getHomeworkTitle());
                if (homeworkEntity.getHomeworkFormat() != null && !"".equals(homeworkEntity.getHomeworkFormat()))
                    homeworkEntity1.setHomeworkFormat(homeworkEntity.getHomeworkFormat());
                if (homeworkEntity.getHomeworkDescription() != null && !"".equals(homeworkEntity.getHomeworkDescription()))
                    homeworkEntity1.setHomeworkDescription(homeworkEntity.getHomeworkDescription());
                if (homeworkEntity.getFileId() != null && !"".equals(homeworkEntity.getFileId())) homeworkEntity1.setFileId(homeworkEntity.getFileId());
                homeworkEntityDao.save(homeworkEntity1);
//            Date date = new Date("2019-29-12")
                webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
            }
        }
        return webApiResult;
    }

    /**
     * 返回班级作业详细信息（拼接class，homework，class_homework）
     * @param userId 用户id
     * @return
     */
    @GetMapping(value = "getClassHomeworkRichInfo")
    public WebApiResult getClassHomeworkRichInfo(Long userId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (userService.isExistedByUserId(userId)) { // 用户存在
            AccountType accountType = userService.getByUserId(userId).getAccountType();
            if (accountType == AccountType.TEACHER) { // 教师
                List<ClassHomeworkRichInfo> classHomeworkRichInfos = new ArrayList<>();
                ClassHomeworkRichInfo classHomeworkRichInfo;// = new ClassHomeworkRichInfo();
                List<HomeworkEntity> homeworkEntities = homeworkEntityDao.getAllByTeacherId(userId);
                ClassHomeworkEntity classHomeworkEntity = new ClassHomeworkEntity();
                Calendar calendar = Calendar.getInstance();
                // 设置默认开始和结束时间
                for (HomeworkEntity homeworkEntity: homeworkEntities) {
                    calendar.set(2000,1,1,1,1,1);
                    classHomeworkEntity.setHomeworkStartTime(calendar.getTime());
                    calendar.set(2999,1,1,1,1,1);
                    classHomeworkEntity.setHomeworkDeadlineTime(calendar.getTime());
                    classHomeworkRichInfo = new ClassHomeworkRichInfo();
                    classHomeworkRichInfo.setHomeworkEntity(homeworkEntity);
                    if (classHomeworkEntityDao.existsByHomeworkId(homeworkEntity.getId())) { // 遍历已发布作业
                        classHomeworkEntity = classHomeworkEntityDao.getByHomeworkId(homeworkEntity.getId());
                        classHomeworkRichInfo.setClassHomeworkId(classHomeworkEntity.getId());
                        classHomeworkRichInfo.setClassName(classesService.getClassById(classHomeworkEntity.getClassId()).getClassName());
                    }
                    classHomeworkRichInfo.setHomeworkStartTime(classHomeworkEntity.getHomeworkStartTime());
                    classHomeworkRichInfo.setHomeworkDeadlineTime(classHomeworkEntity.getHomeworkDeadlineTime());
                    classHomeworkRichInfos.add(classHomeworkRichInfo); // 保存于list中
                }
                webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, classHomeworkRichInfos);
            }
            else if(accountType == AccountType.STUDENT) { // 学生
                //TODO 学生
                List<ClassHomeworkRichInfo> classHomeworkRichInfos = new ArrayList<>();
                ClassHomeworkRichInfo classHomeworkRichInfo;
                List<ClassStudentEntity> classStudentEntityList = classStudentEntityDao.getAllByStudentId(userId); // 获取学生所有-学生班级
//                ClassHomeworkEntity classHomeworkEntity = new ClassHomeworkEntity();
                for (ClassStudentEntity classStudentEntity: classStudentEntityList) { // 遍历所有学生班级
                    List<ClassHomeworkEntity> classHomeworkEntityList = classHomeworkEntityDao.getAllByClassId(classStudentEntity.getClassId());
                    for (ClassHomeworkEntity classHomeworkEntity1: classHomeworkEntityList) { // 遍历每个班级对应的所有发布作业
                        classHomeworkRichInfo = new ClassHomeworkRichInfo();
                        classHomeworkRichInfo.setHomeworkEntity(homeworkEntityDao.getById(classHomeworkEntity1.getHomeworkId())); // 获取作业详细信息
                        classHomeworkRichInfo.setHomeworkStartTime(classHomeworkEntity1.getHomeworkStartTime());
                        classHomeworkRichInfo.setHomeworkDeadlineTime(classHomeworkEntity1.getHomeworkDeadlineTime());
                        classHomeworkRichInfo.setClassHomeworkId(classHomeworkEntity1.getId());
                        classHomeworkRichInfo.setClassName(classesService.getClassById(classHomeworkEntity1.getClassId()).getClassName());
                        classHomeworkRichInfos.add(classHomeworkRichInfo);
                    }
                }
                webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, classHomeworkRichInfos);
            }
        }
        return webApiResult;
    }
    /**
     * 获取家庭作业信息（教师：布置的作业；学生：所在班级的作业）
     * @param userId 用户id（教师/学生）
     * @return
     */
    @GetMapping(value = "getHomework")
    public WebApiResult getHomework(Long userId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (userService.isExistedByUserId(userId)) { // 用户存在
            AccountType accountType = userService.getByUserId(userId).getAccountType();
            if (accountType == AccountType.TEACHER) { // 教师
                List<HomeworkEntity> homeworkEntities = homeworkEntityDao.getAllByTeacherId(userId);
                webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, homeworkEntities);
            }
            else if(accountType == AccountType.STUDENT) { // 学生
                //TODO 学生
            }
        }
        return webApiResult;
    }

    /**
     * 删除作业
     * @param teacherId 教师id
     * @return
     */
    @GetMapping(value = "deleteHomework")
    public WebApiResult deleteHomework(Long teacherId, Long homeworkId) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (homeworkEntityDao.existsByTeacherIdAndId(teacherId, homeworkId)) {
//            classHomeworkEntityDao.deleteAllByHomeworkId(homeworkId); // 先删除班级-作业，解除依赖
            homeworkService.deleteAllByHomeworkId(homeworkId);
            homeworkEntityDao.deleteById(homeworkId); // 再删作业
            webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
        }
        return webApiResult;
    }
}
