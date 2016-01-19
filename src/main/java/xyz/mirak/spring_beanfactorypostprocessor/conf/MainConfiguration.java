package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanDefinitionRegistryPostProcessor;

@Configuration
public class MainConfiguration implements BeanDefinitionRegistryPostProcessor, Ordered {

	private SayenBeanDefinitionRegistryPostProcessor sayenBeanDefinitionRegistryPostProcessor = new SayenBeanDefinitionRegistryPostProcessor();

	public int getOrder() {
		return sayenBeanDefinitionRegistryPostProcessor.getOrder();
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		sayenBeanDefinitionRegistryPostProcessor.postProcessBeanFactory(beanFactory);
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
		sayenBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
	}

	/*
	 * @Bean public static SayenBeanFactoryPostProcessor
	 * sayenBeanFactoryPostProcessor() { return new
	 * SayenBeanFactoryPostProcessor(); }
	 */

	/*
	 * @Bean public static SayenBeanDefinitionRegistryPostProcessor
	 * sayenBeanDefinitionRegistryPostProcessor() { return new
	 * SayenBeanDefinitionRegistryPostProcessor(); }
	 */

	@Bean
	public AutowiredBean autowiredBean() {
		return new AutowiredBean();
	}

	// @Bean
	/* @Transform(type = MegaSuperKarim.class) */
	// public Karim Karim() {
	// return new Karim();
	// }

	@Bean
	@Transform(type = MegaSuperKarim.class)
	public Karim Karim() {
		return new Karim();
	}

	/*@Bean
	public TransformFactoryBean Guillaume() {
		TransformFactoryBean transformFactoryBean = new TransformFactoryBean();
		transformFactoryBean.setObjectType(SuperGuillaume.class);
		return transformFactoryBean;
	}*/

	@Bean
	@Transform(type = SuperGuillaume.class)
	public Guillaume Guillaume() {
		return new Guillaume();
	}
	
	@Bean
	public Yoann Yoann() {
		return new Yoann();
	}

	@Bean
	public Nicolas Nicolas() {
		return new Nicolas();
	}

	@Bean
	public BeanHolder XeanHolder() {
		return new BeanHolder();
	}
}
