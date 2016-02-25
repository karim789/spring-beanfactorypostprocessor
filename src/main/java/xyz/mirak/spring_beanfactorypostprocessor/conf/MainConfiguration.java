package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenImportBeanDefinitionRegistrar;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.TestBeanPostProcessor;

@Configuration
@Import(SayenImportBeanDefinitionRegistrar.class)
public class MainConfiguration extends SayenBeanDefinitionRegistryPostProcessorConfig {

	/*@Bean
	public static SayenBeanDefinitionRegistryPostProcessor sayenBeanDefinitionRegistryPostProcessor() {
		return new SayenBeanDefinitionRegistryPostProcessor();
	}*/

	/*@Bean
	public SayenImportBeanDefinitionRegistrar sayenImportBeanDefinitionRegistrar() {
		return new SayenImportBeanDefinitionRegistrar();
	}*/

	@Bean
	public TestBeanPostProcessor testBeanPostProcessor() {
		return new TestBeanPostProcessor();
	}

	@Bean
	public Boolean pouet() {
		return true;
	}

	@Bean
	public AutowiredBean autowiredBean() {
		return new AutowiredBean();
	}

	@Bean
	@Transform(type = MegaSuperKarim.class)
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

	@Bean
	public BeanHolder beanHolder() {
		return new BeanHolder();
	}
}
