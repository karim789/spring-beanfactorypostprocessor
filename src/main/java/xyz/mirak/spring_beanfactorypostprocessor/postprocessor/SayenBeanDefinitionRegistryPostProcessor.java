package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;
import xyz.mirak.spring_beanfactorypostprocessor.conf.MainConfiguration;
import xyz.mirak.spring_beanfactorypostprocessor.conf.TransformFactoryBean;

public class SayenBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Ordered {

	private static Logger logger = LoggerFactory.getLogger(MainConfiguration.class);

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.debug("rien");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
		//DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) beanFactory;

		for (String originalBeanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition originalBeanDefinition = beanFactory.getBeanDefinition(originalBeanName);
			logger.debug("original beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			if (originalBeanDefinition.isAbstract()) {
				continue;
			}

			Transform sayenAnnotation = getMethodAnnotation(beanFactory, originalBeanDefinition);
			
			/*if (sayenAnnotation == null) {
				Class<?> originalBeanClass = beanFactory.getType(originalBeanName);
				sayenAnnotation = AnnotationUtils.findAnnotation(originalBeanClass, Transform.class);
				*/if (sayenAnnotation == null) {
					continue;
				}/*
			}*/

			Class<? extends Sayan> sayenClass = sayenAnnotation.type();

			RootBeanDefinition sayenFactoryBeanDefinition = new RootBeanDefinition(TransformFactoryBean.class, Autowire.BY_TYPE.value(), true);
			sayenFactoryBeanDefinition.getPropertyValues().add("objectType", sayenClass);
			sayenFactoryBeanDefinition.getPropertyValues().add("singleton", true);
			
			String factoryBeanName = originalBeanName;

			logger.debug("remove beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			beanFactory.removeBeanDefinition(originalBeanName);

			logger.debug("register beanName=" + factoryBeanName + ", " + sayenFactoryBeanDefinition.toString());
			beanFactory.registerBeanDefinition(factoryBeanName, sayenFactoryBeanDefinition);

		}

	}

	private Transform getMethodAnnotation(BeanDefinitionRegistry beanFactory, BeanDefinition originalBeanDefinition) {
		String originalBeanFactoryBeanName = originalBeanDefinition.getFactoryBeanName();
		String originalBeanFactoryMethodName = originalBeanDefinition.getFactoryMethodName();

		if (originalBeanFactoryBeanName == null || originalBeanFactoryMethodName == null) {
			return null;
		}

		Class<?> originalBeanFactoryBeanClass = ClassUtils.getUserClass(((DefaultListableBeanFactory)beanFactory).getType(originalBeanFactoryBeanName));
		try {
			Method method = originalBeanFactoryBeanClass.getMethod(originalBeanFactoryMethodName);
			return AnnotationUtils.getAnnotation(method, Transform.class);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

}
