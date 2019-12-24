package pers.wyj.smartteaching.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/23 23:21
 * @Version 1.0
 */
@Getter
@Setter
public class ClassRichInfo extends ClassesEntity{
    String teacherName; // 教师名
    Date joinTime; // 加入时间
}
