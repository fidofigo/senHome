package com.example;

import com.senhome.api.cms.api.CmsDetailServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-context.xml", "classpath:spring/applicationContext-servlet.xml"})
public class WebApplicationTests {

    @Resource
    CmsDetailServiceApi cmsDetailServiceApi;

	@Test
	public void contextLoads() {
        System.out.println("@@@@@@@@@@@@@@@" + cmsDetailServiceApi.getCmsDetail(1));
	}

}
