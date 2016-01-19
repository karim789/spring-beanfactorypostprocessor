package xyz.mirak.spring_beanfactorypostprocessor.conf;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.mirak.spring_beanfactorypostprocessor.bean.AutowiredBean;

public class TransformFactoryBean implements FactoryBean<Object> {

	@Autowired
	private AutowiredBean pouet;

	private Class<?> objectType;

	boolean singleton = true;

	@Override
	public Object getObject() throws Exception {
		return objectType.getConstructor().newInstance();
	}

	@Override
	public Class<?> getObjectType() {
		return objectType;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}

	public void setObjectType(Class<?> objectType) {
		this.objectType = objectType;
	}

	public AutowiredBean getPouet() {
		return pouet;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

}