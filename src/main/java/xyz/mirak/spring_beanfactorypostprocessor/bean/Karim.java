package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.stereotype.Component;

/*@Transform(type=MegaSuperKarim.class)*/
@Component
public class Karim implements Personne {

	@Override
	public String getName() {
		return "Karim";
	}

}
