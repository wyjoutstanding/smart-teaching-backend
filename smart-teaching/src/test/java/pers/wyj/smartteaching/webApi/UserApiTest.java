package pers.wyj.smartteaching.webApi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import pers.wyj.smartteaching.SmartTeachingApplication;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 14:47
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTeachingApplication.class) // 启动类
public class UserApiTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void login() throws Exception{
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/login").param("accountName", "wuyangjun").param("accountPassword", "8423535")
                .characterEncoding("utf-8")
        ).andReturn();

        int status = result.getResponse().getStatus();
        String message = result.getResponse().getContentAsString();
        System.out.println(result);
        System.out.println(status+"  \n"+message);
    }
}