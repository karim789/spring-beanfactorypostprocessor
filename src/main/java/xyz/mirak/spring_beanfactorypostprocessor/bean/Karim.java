package xyz.mirak.spring_beanfactorypostprocessor.bean;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import xyz.mirak.spring_beanfactorypostprocessor.annotation.Sayen;

@Sayen(type=MegaSuperKarim.class)
@Component
public class Karim implements Personne {

	public String getName() {
		return "Karim";
	}

	@PostConstruct
	public void name() {
		System.out.println("karim post construct");
	}
	
}
