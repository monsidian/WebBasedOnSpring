package com.lyt.community;

import com.lyt.community.dao.AlphaDAO;
import com.lyt.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)

class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
		System.out.println(alphaService.find());
		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);
		AlphaDAO alphaDAO = applicationContext.getBean(AlphaDAO.class);
		System.out.println(alphaDAO.select());

		alphaDAO = applicationContext.getBean(("alphaH"), AlphaDAO.class);
		System.out.println(alphaDAO.select());
	}

	@Autowired	//申明修饰的对象是一个bean,根据类获取bean
		@Qualifier("alphaH")
	private AlphaDAO alphaDAO;

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI(){
		System.out.println(alphaDAO.select());
		System.out.println(alphaService.find());
		System.out.println(simpleDateFormat.format(new Date()));
	}

}
