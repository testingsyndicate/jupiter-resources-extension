package com.testingsyndicate.jupiter.extensions.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AnnotationsTest {

  @ParameterizedTest
  @MethodSource
  void findsAnnotationHierarchically(AnnotatedElement element, String expected) {
    // when
    var actual = Annotations.findHierarchical(element, FindMe.class);

    // then
    var value = actual == null ? null : actual.value();
    assertThat(value).isEqualTo(expected);
  }

  static Stream<Arguments> findsAnnotationHierarchically() throws Exception {
    var clazz = AnnotatedClass.class;
    var annotatedMethod = clazz.getDeclaredMethod("annotatedMethod", String.class, String.class);
    var unannotatedMethod = clazz.getDeclaredMethod("unannotatedMethod");
    return Stream.of(
        arguments(clazz, "class"),
        arguments(clazz.getDeclaredField("annotatedField"), "field"),
        arguments(clazz.getDeclaredField("unannotatedField"), "class"),
        arguments(annotatedMethod, "method"),
        arguments(unannotatedMethod, "class"),
        arguments(annotatedMethod.getParameters()[0], "parameter"),
        arguments(annotatedMethod.getParameters()[1], "method"),
        arguments(UnannotatedClass.class, "package"));
  }

  @Retention(RetentionPolicy.RUNTIME)
  @interface FindMe {
    String value();
  }

  @FindMe("class")
  private static class AnnotatedClass {

    @FindMe("field")
    private String annotatedField;

    private String unannotatedField;

    @FindMe("method")
    private void annotatedMethod(
        @FindMe("parameter") String annotatedParameter, String unannotatedParameter) {}

    private void unannotatedMethod() {}
  }

  private static class UnannotatedClass {}
}
