package com.testingsyndicate.jupiter.extensions.resources;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.util.List;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.SearchOption;

class Annotations {
  private Annotations() {}

  static <T extends Annotation> T findHierarchical(AnnotatedElement element, Class<T> annotation) {
    var result =
        element instanceof Class<?> clazz
            ? findAnnotation(clazz, annotation, SearchOption.INCLUDE_ENCLOSING_CLASSES)
            : findAnnotation(element, annotation);

    if (result.isPresent()) {
      return result.get();
    }

    if (element instanceof Parameter parameter) {
      return findHierarchical(parameter.getDeclaringExecutable(), annotation);
    }

    if (element instanceof Member member) {
      return findHierarchical(member.getDeclaringClass(), annotation);
    }

    if (element instanceof Class<?> clazz) {
      return findHierarchical(clazz.getPackage(), annotation);
    }

    return null;
  }

  static <T extends Annotation> List<AnnotatedField<T>> findAnnotatedFields(
      Class<?> clazz, Class<T> annotation) {
    return AnnotationSupport.findAnnotatedFields(clazz, annotation).stream()
        .map(f -> new AnnotatedField<>(f, f.getAnnotation(annotation)))
        .toList();
  }

  record AnnotatedField<T extends Annotation>(Field field, T annotation) {}
}
