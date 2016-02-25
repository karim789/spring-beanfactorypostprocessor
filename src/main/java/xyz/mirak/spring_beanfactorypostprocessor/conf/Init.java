package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanDefinitionRegistryPostProcessor;

public class Init implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		applicationContext.addBeanFactoryPostProcessor(new SayenBeanDefinitionRegistryPostProcessor());

	}

}
