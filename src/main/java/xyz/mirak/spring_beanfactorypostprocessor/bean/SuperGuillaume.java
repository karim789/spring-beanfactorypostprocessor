package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

@Component
public class SuperGuillaume extends Guillaume implements Sayan {

	@Override
	public String getName() {
		return "SuperGuillaume";
	}

}
