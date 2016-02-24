package xyz.mirak.spring_beanfactorypostprocessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class KarimBeanHolder {

	@Autowired
	@Qualifier("Karim")
	private MegaSuperKarim karim;

	public Personne getKarim() {
		return karim;
	}

}
