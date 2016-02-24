package xyz.mirak.spring_beanfactorypostprocessor;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.conf.MainConfiguration;

/**
 * Hello world!
 * 
 */
public class App {

	private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		System.out.println("Hello World!");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfiguration.class);

		Map<String, Personne> beansOfType = ctx.getBeansOfType(Personne.class);
		for (Entry<String, Personne> e : beansOfType.entrySet()) {
			logger.debug(e.getValue().getName());

		}
		BeanHolder karimBeanHolder = ctx.getBean(BeanHolder.class);
		Assert.notNull(karimBeanHolder.getKarim());
		ctx.destroy();
		ctx.close();
	}
}
