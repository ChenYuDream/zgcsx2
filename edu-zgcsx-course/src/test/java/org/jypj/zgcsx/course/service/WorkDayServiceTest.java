package org.jypj.zgcsx.course.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkDayServiceTest {

    @Autowired
    private WorkDayService workDayService;

    @Test
    public void initData() {
        String[] xqArr = {"37BCC70C9C9B4497B2EAEA429EA79B42", "46FD3EE0E85D45BE8B8692D19C89477E", "97F98909E910402AA723FC1270AAFE4C"};
        Integer[] xn = {2016, 2017};
        Integer[] xq = {1, 2};
        //校区
        for (int i = 0; i < xqArr.length; i++) {
            //学年
            for (int j = 0; j < xn.length; j++) {
                //学期
                for (int k = 0; k < xq.length; k++) {
                    //第几周
                    for (int l = 0; l <23 ; l++) {
                        //周几
                        for (int m = 0; m <7 ; m++) {
                            //第几节
                            for (int n = 0; n < 7; n++) {

                            }
                        }
                    }
                }
            }
        }


    }
}