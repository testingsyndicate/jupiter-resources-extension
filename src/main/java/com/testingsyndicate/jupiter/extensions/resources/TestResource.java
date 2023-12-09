package com.testingsyndicate.jupiter.extensions.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

/** Specifies the options for injecting a resource into a test parameter. */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@ExtendWith(ResourcesExtension.class)
public @interface TestResource {
  /** Name of the file of the resource to be injected */
  String value();

  /** Charset which should be used when reading this resource */
  String charset() default "";
}
