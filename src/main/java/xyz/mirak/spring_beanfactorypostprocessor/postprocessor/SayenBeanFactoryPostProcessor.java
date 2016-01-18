package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;

public class SayenBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(SayenBeanFactoryPostProcessor.class);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) bf;

		for (String originalBeanName : beanFactory.getBeanDefinitionNames()) {
			logger.debug("beanName:" + originalBeanName);

			BeanDefinition originalBeanDefinition = beanFactory.getBeanDefinition(originalBeanName);
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

			AnnotatedGenericBeanDefinition sayenFactoryBeanDefinition = new AnnotatedGenericBeanDefinition(TransformFactoryBean.class);
			setPropertyValues(sayenClass, sayenFactoryBeanDefinition);

			String factoryBeanMethodName = factoryMethodName(sayenFactoryBeanDefinition);
			String factoryBeanName = originalBeanClass.getSimpleName() + sayenClass.getSimpleName() + "FactoryBean";

			logger.debug("register beanName=" + factoryBeanName + ", " + sayenFactoryBeanDefinition.toString());
			beanFactory.registerBeanDefinition(factoryBeanName, sayenFactoryBeanDefinition);

			String newBeanName = originalBeanName;
			//BeanDefinition newBeanDefinition = new AnnotatedGenericBeanDefinition(sayenClass);
			BeanDefinition newBeanDefinition = originalBeanDefinition;
			//newBeanDefinition.setPrimary(originalBeanDefinition.isPrimary());
			//newBeanDefinition.setScope(originalBeanDefinition.getScope());
			//newBeanDefinition.setAutowireCandidate(originalBeanDefinition.isAutowireCandidate());
			//newBeanDefinition.setRole(originalBeanDefinition.getRole());
			//newBeanDefinition.setLazyInit(originalBeanDefinition.isLazyInit());
			//newBeanDefinition.setOriginatingBeanDefinition(originalBeanDefinition);
			newBeanDefinition.setFactoryBeanName(factoryBeanName);
			newBeanDefinition.setFactoryMethodName(factoryBeanMethodName);
			//newBeanDefinition.setBeanClassName(originalBeanDefinition.getBeanClassName());
			
			//logger.debug("remove beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			//beanFactory.removeBeanDefinition(originalBeanName);
			
			logger.debug("register beanName=" + newBeanName + ", " + newBeanDefinition.toString());
			//beanFactory.registerBeanDefinition(newBeanName, newBeanDefinition);

		}

	}

	private Transform getMethodAnnotation(DefaultListableBeanFactory beanFactory, BeanDefinition originalBeanDefinition) {
		String originalBeanFactoryBeanName = originalBeanDefinition.getFactoryBeanName();
		String originalBeanFactoryMethodName = originalBeanDefinition.getFactoryMethodName();

		if (originalBeanFactoryBeanName == null || originalBeanFactoryMethodName == null) {
			return null;
		}

		Class<?> originalBeanFactoryBeanClass = ClassUtils.getUserClass(beanFactory.getType(originalBeanFactoryBeanName));
		try {
			Method method = originalBeanFactoryBeanClass.getMethod(originalBeanFactoryMethodName);
			return AnnotationUtils.getAnnotation(method, Transform.class);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

	private void setPropertyValues(Class<?> clazz, AnnotatedGenericBeanDefinition beanDefinition) {
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add("objectType", clazz);
		beanDefinition.setPropertyValues(pvs);
	}

	private String factoryMethodName(AnnotatedGenericBeanDefinition beanDefinition) {
		AnnotationMetadata metadata = beanDefinition.getMetadata();
		Set<MethodMetadata> annotatedMethods = metadata.getAnnotatedMethods(Bean.class.getName());
		Assert.isTrue(1 == annotatedMethods.size(), "just one factory method must have @Bean annotation");
		MethodMetadata factoryMethod = annotatedMethods.iterator().next();
		return factoryMethod.getMethodName();
	}

	public static class TransformFactoryBean {

		@Autowired
		private AutowiredBean pouet;

		private Class<Sayan> objectType;

		@Bean
		public Sayan getObject() throws Exception {
			return objectType.getConstructor().newInstance();
		}

		public void setObjectType(Class<Sayan> objectType) {
			this.objectType = objectType;
		}

		public AutowiredBean getPouet() {
			return pouet;
		}

	}

}
