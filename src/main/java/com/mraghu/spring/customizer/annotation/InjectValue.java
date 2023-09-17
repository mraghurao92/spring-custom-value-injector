package com.mraghu.spring.customizer.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value(value = "")
public @interface InjectValue {

    @AliasFor(annotation = org.springframework.beans.factory.annotation.Value.class)
    String value();
    String name() default "";
    String expireDate() default "";

    String[] usedBy() default {};
}