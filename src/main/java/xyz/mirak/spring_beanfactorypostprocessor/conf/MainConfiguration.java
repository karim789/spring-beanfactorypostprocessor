package xyz.mirak.spring_beanfactorypostprocessor.conf;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;
import xyz.mirak.spring_beanfactorypostprocessor.bean.SuperGuillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;

@Configuration
public class MainConfiguration implements BeanDefinitionRegistryPostProcessor, Ordered {

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
			//sayenFactoryBeanDefinition.setTargetType(sayenClass);
			setPropertyValues(sayenFactoryBeanDefinition, "objectType", sayenClass);
			setPropertyValues(sayenFactoryBeanDefinition, "singleton", true);
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

	private void setPropertyValues(RootBeanDefinition beanDefinition, String propertyName, Object value) {
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add(propertyName, value);
		beanDefinition.setPropertyValues(pvs);
	}

	/*
	 * @Bean public static SayenBeanFactoryPostProcessor
	 * sayenBeanFactoryPostProcessor() { return new
	 * SayenBeanFactoryPostProcessor(); }
	 */

	/*
	 * @Bean public static SayenBeanDefinitionRegistryPostProcessor
	 * sayenBeanDefinitionRegistryPostProcessor() { return new
	 * SayenBeanDefinitionRegistryPostProcessor(); }
	 */

	@Bean
	public AutowiredBean autowiredBean() {
		return new AutowiredBean();
	}

	// @Bean
	/* @Transform(type = MegaSuperKarim.class) */
	// public Karim Karim() {
	// return new Karim();
	// }

	@Bean
	@Transform(type = MegaSuperKarim.class)
	public Karim Karim() {
		return new Karim();
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
	public BeanHolder XeanHolder() {
		return new BeanHolder();
	}
}
