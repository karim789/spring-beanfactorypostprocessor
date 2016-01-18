package xyz.mirak.spring_beanfactorypostprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xyz.mirak.spring_beanfactorypostprocessor.bean.Sayan;

@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Transform {
	
	Class<? extends Sayan> type();
	
}
