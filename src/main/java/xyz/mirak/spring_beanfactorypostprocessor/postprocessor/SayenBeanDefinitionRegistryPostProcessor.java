package xyz.mirak.spring_beanfactorypostprocessor.postprocessor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.StandardMethodMetadata;

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
		
		for (String originalBeanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition originalBeanDefinition = beanFactory.getBeanDefinition(originalBeanName);
			logger.debug("original beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			if (originalBeanDefinition.isAbstract()) {
				continue;
			}
			Object source = originalBeanDefinition.getSource();
			if (!(source instanceof StandardMethodMetadata)) {
				continue;
			}

			StandardMethodMetadata factoryMethodMetadata = (StandardMethodMetadata) source;
			Method factoryMethod = factoryMethodMetadata.getIntrospectedMethod();
			if (!isFactoryMethod(originalBeanDefinition, factoryMethod)) {
				continue;
			}

			Transform sayenAnnotation = findAnnotationOnMethod(factoryMethod);
			if (sayenAnnotation == null) {
				sayenAnnotation = findAnnotationOnReturnType(factoryMethod);
				if (sayenAnnotation == null) {
					continue;
				} else {
					logger.debug("found annotation " + sayenAnnotation.toString() + " on return Type of method " + factoryMethod.toGenericString());
				}
			} else {
				logger.debug("found annotation " + sayenAnnotation.toString() + " on method " + factoryMethod.toGenericString());
			}

			Class<? extends Sayan> sayenClass = sayenAnnotation.type();

			RootBeanDefinition sayenFactoryBeanDefinition = new RootBeanDefinition(TransformFactoryBean.class, 3, true); // Autowire.BY_TYPE.value()
			sayenFactoryBeanDefinition.getPropertyValues().add("objectType", sayenClass);
			sayenFactoryBeanDefinition.getPropertyValues().add("singleton", true);
			String factoryBeanName = originalBeanName;

			logger.debug("remove beanName=" + originalBeanName + ", " + originalBeanDefinition.toString());
			beanFactory.removeBeanDefinition(originalBeanName);

			logger.debug("register beanName=" + factoryBeanName + ", " + sayenFactoryBeanDefinition.toString());
			beanFactory.registerBeanDefinition(factoryBeanName, sayenFactoryBeanDefinition);

		}

	}

	private boolean isFactoryMethod(BeanDefinition rbd, Method candidate) {
		return (candidate != null && candidate.getName().equals(rbd.getFactoryMethodName()) && isBeanAnnotated(candidate));
	}

	private boolean isBeanAnnotated(Method method) {
		return AnnotationUtils.findAnnotation(method, Bean.class) != null;
	}

	private Transform findAnnotationOnMethod(Method method) {
		return AnnotationUtils.getAnnotation(method, Transform.class);
	}

	private Transform findAnnotationOnReturnType(Method method) {
		Class<?> returnType = method.getReturnType();
		return AnnotationUtils.findAnnotation(returnType, Transform.class);
	}

}
