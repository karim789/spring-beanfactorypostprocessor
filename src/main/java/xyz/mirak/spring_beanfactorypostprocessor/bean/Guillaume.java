package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

@Component
public class Guillaume implements Personne {
	@Override
	public String getName() {
		return "Guillaume";
	}

}
