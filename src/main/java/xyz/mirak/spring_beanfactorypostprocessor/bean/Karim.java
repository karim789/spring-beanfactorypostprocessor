package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

/*@Transform(type=MegaSuperKarim.class)*/
@Component
public class Karim implements Personne, TestBeanPostProcessorAfterInitialization {

	@Override
	public String getName() {
		return "Karim";
	}

	boolean isPostProcessAfterInitialization;

	/*
	 * (non-Javadoc)
	 * 
	 * @see xyz.mirak.spring_beanfactorypostprocessor.bean.
	 * PostProcessAfterInitialization#isPostProcessAfterInitialization()
	 */
	@Override
	public boolean isPostProcessAfterInitialization() {
		return isPostProcessAfterInitialization;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see xyz.mirak.spring_beanfactorypostprocessor.bean.
	 * PostProcessAfterInitialization#setPostProcessAfterInitialization(boolean)
	 */
	@Override
	public void setPostProcessAfterInitialization(boolean isPostProcessAfterInitialization) {
		this.isPostProcessAfterInitialization = isPostProcessAfterInitialization;
	}

}
