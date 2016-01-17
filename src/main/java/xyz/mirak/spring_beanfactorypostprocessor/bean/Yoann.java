package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

@Component
public class Yoann implements Personne {
	@Override
	public String getName() {
		return "Yoann";
	}
}
