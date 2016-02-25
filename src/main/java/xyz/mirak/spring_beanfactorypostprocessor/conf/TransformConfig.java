package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanFactoryPostProcessor;

@Configuration
@Profile("Transform")
public class TransformConfig {

	@Bean
	static public SayenBeanFactoryPostProcessor sayenBeanFactoryPostProcessor() {
		return new SayenBeanFactoryPostProcessor();
	}

}
