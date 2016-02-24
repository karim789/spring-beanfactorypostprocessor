package xyz.mirak.spring_beanfactorypostprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperSayan;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.conf.MainConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = MainConfiguration.class) })
public class AppTest {
	private static Logger logger = LoggerFactory.getLogger(AppTest.class);

	@Autowired
	private Personne[] personnes;

	@Autowired
	private BeanHolder beanHolder;

	@Autowired
	private Yoann yoann;

	@Autowired
	private Nicolas nicolas;

	@Autowired
	private Guillaume guillaume;

	@Autowired
	private Karim karim;

	@Autowired
	@Qualifier("toto")
	private Personne toto;

	@Test
	public void test() {
		for (Personne e : personnes) {
			if (e != null) {
				logger.info(e.getName());
			}
		}

		Assert.isInstanceOf(Yoann.class, yoann);
		Assert.isInstanceOf(Nicolas.class, nicolas);
		Assert.isInstanceOf(Sayan.class, guillaume);
		Assert.isInstanceOf(SuperGuillaume.class, guillaume);
		Assert.isInstanceOf(Karim.class, karim);
		Assert.isInstanceOf(SuperSayan.class, karim);
		Assert.isInstanceOf(MegaSuperKarim.class, karim);

		Assert.notNull(beanHolder.getKarim());
		Assert.isTrue(karim.isPostProcessAfterInitialization(), "bean should be post processed after initialisation");
	}

}
