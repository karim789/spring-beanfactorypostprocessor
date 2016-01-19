package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;

public class SayenBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

	private static Logger logger = LoggerFactory.getLogger(SayenBeanFactoryPostProcessor.class);

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) bf;

		for (String originalBeanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition originalBeanDefinition = beanFactory.getBeanDefinition(originalBeanName);
			logger.debug("original beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			if (originalBeanDefinition.isAbstract()) {
				continue;
			}

			Transform sayenAnnotation = getMethodAnnotation(beanFactory, originalBeanDefinition);
			Class<?> originalBeanClass = beanFactory.getType(originalBeanName);

			if (sayenAnnotation == null) {
				sayenAnnotation = AnnotationUtils.findAnnotation(originalBeanClass, Transform.class);
				if (sayenAnnotation == null) {
					continue;
				}
			}

			Class<? extends Sayan> sayenClass = sayenAnnotation.type();

			AnnotatedGenericBeanDefinition sayenFactoryBeanDefinition = new AnnotatedGenericBeanDefinition(
					TransformFactoryBean.class, Autowire.BY_TYPE.value(), true););
			setPropertyValues(sayenFactoryBeanDefinition, "objectType", sayenClass);
			setPropertyValues(sayenFactoryBeanDefinition, "singleton", true);
			String factoryBeanName = originalBeanName;

			logger.debug("remove beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			beanFactory.removeBeanDefinition(originalBeanName);

			logger.debug("register beanName=" + factoryBeanName + ", "
					+ sayenFactoryBeanDefinition.toString());
			beanFactory.registerBeanDefinition(factoryBeanName, sayenFactoryBeanDefinition);

		}

	}

	private Transform getMethodAnnotation(DefaultListableBeanFactory beanFactory,
			BeanDefinition originalBeanDefinition) {
		String originalBeanFactoryBeanName = originalBeanDefinition.getFactoryBeanName();
		String originalBeanFactoryMethodName = originalBeanDefinition.getFactoryMethodName();

		if (originalBeanFactoryBeanName == null || originalBeanFactoryMethodName == null) {
			return null;
		}

		Class<?> originalBeanFactoryBeanClass = ClassUtils.getUserClass(beanFactory
				.getType(originalBeanFactoryBeanName));
		try {
			Method method = originalBeanFactoryBeanClass.getMethod(originalBeanFactoryMethodName);
			return AnnotationUtils.getAnnotation(method, Transform.class);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

	private void setPropertyValues(AnnotatedGenericBeanDefinition beanDefinition, String propertyName,
			Object value) {
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add(propertyName, value);
		beanDefinition.setPropertyValues(pvs);
	}

	public static class TransformFactoryBean implements FactoryBean {

		@Autowired
		private AutowiredBean pouet;

		private Class objectType;

		boolean singleton = true;

		@Override
		public Karim getObject() throws Exception {
			return (Karim) objectType.getConstructor().newInstance();
		}

		@Override
		public Class getObjectType() {
			return objectType;
		}

		@Override
		public boolean isSingleton() {
			return singleton;
		}

		public void setObjectType(Class objectType) {
			this.objectType = objectType;
		}

		public AutowiredBean getPouet() {
			return pouet;
		}

		public void setSingleton(boolean singleton) {
			this.singleton = singleton;
		}

	}

}
