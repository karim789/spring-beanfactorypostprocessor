package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.KarimBeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanFactoryPostProcessor;

@Configuration
public class MainConfiguration {

	@Bean
	public static SayenBeanFactoryPostProcessor sayenBeanFactoryPostProcessor() {
		return new SayenBeanFactoryPostProcessor();
	}

	@Bean
	public AutowiredBean autowiredBean() {
		return new AutowiredBean();
	}

	@Bean
	@Transform(type = SuperKarim.class)
	public Karim Karim() {
		return new Karim();
	}

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
	public KarimBeanHolder karimBeanHolder() {
		return new KarimBeanHolder();
	}
}
