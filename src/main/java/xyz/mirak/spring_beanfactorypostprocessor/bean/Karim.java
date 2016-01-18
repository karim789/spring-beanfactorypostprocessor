package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Transform;

//@Transform(type=MegaSuperKarim.class)
@Component
public class Karim implements Personne {

	public String getName() {
		return "Karim";
	}

}
