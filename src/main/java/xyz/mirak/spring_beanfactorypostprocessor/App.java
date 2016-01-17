package xyz.mirak.spring_beanfactorypostprocessor;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import xyz.mirak.spring_beanfactorypostprocessor.bean.KarimBeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.conf.MainConfiguration;

/**
 * Hello world!
 * 
 */
public class App
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(MainConfiguration.class);
		ctx.refresh();
		Map<String, Personne> beansOfType = ctx.getBeansOfType(Personne.class);
		for (Entry<String, Personne> e : beansOfType.entrySet()) {
			System.out.println(e.getValue().getName());
		}
		KarimBeanHolder injectedBean = ctx.getBean(KarimBeanHolder.class);
		Assert.notNull(injectedBean.getKarim());
		ctx.destroy();
		ctx.close();
	}
}
