package com.openxsl.studycloud.feign.invoker.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

@Conditional(PropertyMissingCondition.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionalOnMissingProperty {
	
	@AliasFor("names")
	String[] value() default {};
	
	String[] names() default {};

}
