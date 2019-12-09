package pers.wyj.smartteaching.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.wyj.smartteaching.model.ClassesEntity;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/9 20:14
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassesServiceTest {
    @Autowired
    ClassesService classesService;
    @Test
    public void addClass() {
        ClassesEntity classesEntity = new ClassesEntity();
        classesEntity.setClassStatus(1L);
        classesEntity.setClassName("c语言1");
        classesEntity.setTeacherId(1L);
        classesEntity.setClassType("C");
        classesService.addClass(classesEntity);
    }

    @Test
    public void getClassById() {
    }

    @Test
    public void getClassByTeacherId() {
    }

    @Test
    public void getClassByInviteCode() {
    }
}