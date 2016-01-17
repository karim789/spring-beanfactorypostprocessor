package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class KarimBeanHolder {

	@Autowired
	@Qualifier("Karim")
	private Personne karim;

	public Personne getKarim() {
		return karim;
	}

}
