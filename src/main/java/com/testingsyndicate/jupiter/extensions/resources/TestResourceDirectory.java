package com.testingsyndicate.jupiter.extensions.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Specifies a default directory from which to load test resources */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
public @interface TestResourceDirectory {
  /** Directory name to set as the default */
  String value();
}
