package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;

@Component
@Transform(type = SuperGuillaume.class)
public class Guillaume implements Personne {
	@Override
	public String getName() {
		return "Guillaume";
	}

}
