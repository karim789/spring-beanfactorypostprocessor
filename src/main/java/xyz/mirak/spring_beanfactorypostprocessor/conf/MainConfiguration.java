package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanFactoryPostProcessor;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanFactoryPostProcessor.TransformFactoryBean;

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

	//@Bean
	/*@Transform(type = MegaSuperKarim.class)*/
	//public Karim Karim() {
	//	return new Karim();
	//}

	@Bean
	@Transform(type = MegaSuperKarim.class)
	public TransformFactoryBean Karim() {
		TransformFactoryBean transformFactoryBean = new TransformFactoryBean();
		transformFactoryBean.setObjectType(Karim.class);
		return transformFactoryBean;
	}

	@Bean
	public TransformFactoryBean Guillaume() {
		TransformFactoryBean transformFactoryBean = new TransformFactoryBean();
		transformFactoryBean.setObjectType(SuperGuillaume.class);
		return transformFactoryBean;
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
	public BeanHolder beanHolder() {
		return new BeanHolder();
	}
}
