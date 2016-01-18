package xyz.mirak.spring_beanfactorypostprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.KarimBeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperSayan;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.conf.MainConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = MainConfiguration.class) })
public class AppTest {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	Personne[] personnes;

	//@Autowired
	//KarimBeanHolder karimBeanHolder;
	
	@Autowired
	Yoann yoann;

	@Autowired
	Nicolas nicolas;
	
	@Autowired
	Guillaume guillaume;
	
	@Autowired
	Karim karim;
	
	@Test
	public void test() {
		for (Personne e : personnes) {
			logger.debug(e.getName());
		}
		
		Assert.isInstanceOf(Yoann.class, yoann);
		Assert.isInstanceOf(Nicolas.class, nicolas);
		Assert.isInstanceOf(Sayan.class, guillaume);
		Assert.isInstanceOf(SuperGuillaume.class, guillaume);
		//Assert.isInstanceOf(Karim.class, karim);
		//Assert.isInstanceOf(SuperSayan.class, karim);
		Assert.isInstanceOf(MegaSuperKarim.class, karim);
		
		//Assert.notNull(karimBeanHolder.getKarim());
	}
	
	
}
