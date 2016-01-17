package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.KarimBeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanFactoryPostProcessor;

@Configuration
@ComponentScan(basePackageClasses = xyz.mirak.spring_beanfactorypostprocessor.bean.Personne.class)
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
	public KarimBeanHolder karimBeanHolder() {
		return new KarimBeanHolder();
	}

	@Bean
	public Karim Karim() {
		return new Karim();
	}

	@Bean
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

}
