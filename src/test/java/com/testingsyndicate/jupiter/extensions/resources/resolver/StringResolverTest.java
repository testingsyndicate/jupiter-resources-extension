package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.StringReader;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class StringResolverTest {

  @Test
  void returnsString(@TestResource("wibble.txt") String actual) {
    // then
    assertThat(actual).isEqualTo("wibble");
  }

  @Test
  void returnsStringWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") String actual) {
    // then
    assertThat(actual).isEqualTo("\u30bc\u30ea\u30fc");
  }

  @Test
  void returnsCharSequence(@TestResource("wibble.txt") CharSequence actual) {
    // then
    assertThat(actual).isEqualTo("wibble");
  }

  @Test
  void returnsCharSequenceWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") CharSequence actual) {
    // then
    assertThat(actual).isEqualTo("\u30bc\u30ea\u30fc");
  }

  @Test
  void returnsStringBuilder(@TestResource("wibble.txt") StringBuilder actual) {
    // then
    assertThat(actual.toString()).isEqualTo("wibble");
  }

  @Test
  void returnsStringBuilderWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") StringBuilder actual) {
    // then
    assertThat(actual.toString()).isEqualTo("\u30bc\u30ea\u30fc");
  }

  @Test
  void returnsStringBuffer(@TestResource("wibble.txt") StringBuffer actual) {
    // then
    assertThat(actual.toString()).isEqualTo("wibble");
  }

  @Test
  void returnsStringBufferWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") StringBuffer actual) {
    // then
    assertThat(actual.toString()).isEqualTo("\u30bc\u30ea\u30fc");
  }

  @Test
  void returnsStringReader(@TestResource("wibble.txt") StringReader actual) {
    // when
    try (var scanner = new Scanner(actual)) {
      // then
      assertThat(scanner.nextLine()).isEqualTo("wibble");
    }
  }

  @Test
  void returnsStringReaderWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") StringReader actual) {
    // when
    try (var scanner = new Scanner(actual)) {
      // then
      assertThat(scanner.nextLine()).isEqualTo("\u30bc\u30ea\u30fc");
    }
  }
}
