package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

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
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.Assert;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Sayen;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperSayen;

public class SayenBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(SayenBeanFactoryPostProcessor.class);

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory d = (DefaultListableBeanFactory) beanFactory;

		String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

		for (String beanName : beanDefinitionNames) {
			logger.info("beanName:" + beanName);

			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			if (beanDefinition.isAbstract()) {
				continue;
			}

			Class<?> type = beanFactory.getType(beanName);
			Sayen findAnnotation = AnnotationUtils.findAnnotation(type, Sayen.class);
			if (findAnnotation == null) {
				continue;
			}
			logger.info(beanName + " est Super Sayen");

			Class<? extends SuperSayen> sayenType = findAnnotation.type();

			AnnotatedGenericBeanDefinition sayenFactoryBeanDefinition = new AnnotatedGenericBeanDefinition(
					SayenFactoryBean.class);
			MutablePropertyValues pvs = new MutablePropertyValues();
			pvs.add("objectType", sayenType);
			sayenFactoryBeanDefinition.setPropertyValues(pvs);

			AnnotationMetadata metadata = sayenFactoryBeanDefinition.getMetadata();
			Set<MethodMetadata> annotatedMethods = metadata.getAnnotatedMethods(Bean.class.getName());
			Assert.isTrue(1 == annotatedMethods.size(), "just one factory method must have @Bean annotation");
			MethodMetadata factoryMethod = annotatedMethods.iterator().next();
			String factoryMethodName = factoryMethod.getMethodName();

			String factoryBeanName = type.getSimpleName() + sayenType.getSimpleName() + "FactoryBean";
			d.registerBeanDefinition(factoryBeanName, sayenFactoryBeanDefinition);
			logger.debug("register beanName=" + factoryBeanName + ", "
					+ sayenFactoryBeanDefinition.toString());
			d.removeBeanDefinition(beanName);
			logger.debug("remove beanName=" + beanName + ", " + beanDefinition.toString());

			AnnotatedGenericBeanDefinition newBeanDefinition = new AnnotatedGenericBeanDefinition(
					sayenType);
			newBeanDefinition.setFactoryBeanName(factoryBeanName);

			newBeanDefinition.setFactoryMethodName(factoryMethodName);
			newBeanDefinition.setBeanClassName(Personne.class.getName());
			d.registerBeanDefinition(beanName, newBeanDefinition);
			logger.debug("register beanName=" + beanName + ", " + newBeanDefinition.toString());

		}

	}

	public static class SayenFactoryBean {

		@Autowired
		private AutowiredBean pouet;

		private Class<SuperSayen> objectType;

		@Bean
		public SuperSayen getObject() throws Exception {
			SuperSayen newInstance = objectType.getConstructor().newInstance();
			return newInstance;
		}

		public void setObjectType(Class<SuperSayen> objectType) {
			this.objectType = objectType;
		}

	}

}
