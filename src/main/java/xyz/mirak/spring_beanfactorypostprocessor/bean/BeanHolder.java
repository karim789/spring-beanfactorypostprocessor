package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanHolder {

	@Autowired
	private Guillaume gui;

	@Autowired
	private Karim karim;

	public Personne getKarim() {
		return karim;
	}

}
