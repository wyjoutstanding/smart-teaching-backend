package pers.wyj.smartteaching.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/22 21:48
 * @Version 1.0
 */
@Getter
@Setter
// 拼接班级，作业信息返回
public class ClassHomeworkRichInfo {
    Long classHomeworkId; // 班级作业id
    String className; // 班级名称
    Date homeworkPostTime; // 发布时间
    Date homeworkDeadlineTime;  // 截止时间
    Date homeworkStartTime;  // 开始时间
    HomeworkEntity homeworkEntity; // 作业信息
}