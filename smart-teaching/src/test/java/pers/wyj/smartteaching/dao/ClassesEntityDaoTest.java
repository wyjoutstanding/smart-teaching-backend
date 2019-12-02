package pers.wyj.smartteaching.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pers.wyj.smartteaching.model.ClassesEntity;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:40
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//这个没有好像也行？？由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration
@WebAppConfiguration
public class ClassesEntityDaoTest {
    @Autowired
    ClassesEntityDao classesEntityDao; // 自动注入
    @Test
    public void test0() {
//        ClassesEntity classesEntity1 = classesEntityDao.findById(1L)).;
        ClassesEntity classesEntity = classesEntityDao.getById(1L);
        System.out.println(classesEntity);
        classesEntity = classesEntityDao.getByClassName("一班");
        System.out.println(classesEntity);
    }
}