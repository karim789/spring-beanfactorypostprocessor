package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;

import xyz.mirak.spring_beanfactorypostprocessor.bean.TestBeanPostProcessorAfterInitialization;

public class TestBeanPostProcessor implements BeanPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(TestBeanPostProcessor.class);

	@Autowired
	@Qualifier("pouet")
	private Boolean pouet;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		logger.info("postProcessBeforeInitialization :" + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		logger.info("postProcessAfterInitialization :" + beanName);
		if (bean instanceof TestBeanPostProcessorAfterInitialization) {
			((TestBeanPostProcessorAfterInitialization) bean).setPostProcessAfterInitialization(true);
		}
		return bean;
	}

}
