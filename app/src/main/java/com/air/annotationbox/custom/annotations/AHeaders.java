package com.air.annotationbox.custom.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by air on 1/5/18.
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface AHeaders {
    String[] value();
}