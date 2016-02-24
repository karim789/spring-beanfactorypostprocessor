package xyz.mirak.spring_beanfactorypostprocessor.conf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;
import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;
import xyz.mirak.spring_beanfactorypostprocessor.bean.BeanHolder;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Guillaume;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Karim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.MegaSuperKarim;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Nicolas;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Personne;
import xyz.mirak.spring_beanfactorypostprocessor.bean.Yoann;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.SayenBeanDefinitionRegistryPostProcessor;
import xyz.mirak.spring_beanfactorypostprocessor.postprocessor.TestBeanPostProcessor;

@Configuration
public class MainConfiguration implements BeanDefinitionRegistryPostProcessor, Ordered {

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

	@Bean
	public TestBeanPostProcessor testBeanPostProcessor() {
		return new TestBeanPostProcessor();
	}

	@Bean
	public Boolean pouet() {
		return true;
	}

	@Bean
	public AutowiredBean autowiredBean() {
		return new AutowiredBean();
	}

	@Bean
	@Transform(type = MegaSuperKarim.class)
	public Karim Karim() {
		return new Karim();
	}

	@Bean
	public Guillaume Guillaume() {
		return new Guillaume();
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
	public Personne toto() {
		InvocationHandler h = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
			}
		};
		return (Personne) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] { Personne.class }, h);

	}

	@Bean
	public BeanHolder beanHolder() {
		return new BeanHolder();
	}
}
