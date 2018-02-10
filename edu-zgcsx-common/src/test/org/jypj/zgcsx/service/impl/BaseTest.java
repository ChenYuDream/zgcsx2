package org.jypj.zgcsx.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jypj.zgcsx.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jian_wu on 2017/11/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration
        ({"classpath:spring/*.xml"}) //加载配置文件
public class BaseTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void a(){

        resourceService.selcetMenu("2c909ee45fe7707c015fe77fc0530002");

    }

}
