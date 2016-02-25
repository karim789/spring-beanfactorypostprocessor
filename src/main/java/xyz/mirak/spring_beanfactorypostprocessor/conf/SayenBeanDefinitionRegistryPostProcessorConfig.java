package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;

import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanDefinitionRegistryPostProcessor;

/*@Configuration*/
public class SayenBeanDefinitionRegistryPostProcessorConfig implements BeanDefinitionRegistryPostProcessor, Ordered {

	private SayenBeanDefinitionRegistryPostProcessor sayenBeanDefinitionRegistryPostProcessor = new SayenBeanDefinitionRegistryPostProcessor();

	@Override
	public int getOrder() {
		return sayenBeanDefinitionRegistryPostProcessor.getOrder();
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		sayenBeanDefinitionRegistryPostProcessor.postProcessBeanFactory(beanFactory);
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
		sayenBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(beanFactory);
	}

}
