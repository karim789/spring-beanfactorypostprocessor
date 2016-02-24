package xyz.mirak.spring_beanfactorypostprocessor.bean;

public interface TestBeanPostProcessorAfterInitialization {

	boolean isPostProcessAfterInitialization();

	void setPostProcessAfterInitialization(boolean isPostProcessAfterInitialization);

}