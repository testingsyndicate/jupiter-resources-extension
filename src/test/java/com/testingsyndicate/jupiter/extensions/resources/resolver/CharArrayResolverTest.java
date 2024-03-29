package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharArrayResolverTest {

  @Nested
  class PrimitiveCharArrayResolverTest {
    @Test
    void returnsCharArray(@TestResource("wibble.txt") char[] actual) {
      // then
      assertThat(actual).isEqualTo(new char[] {'w', 'i', 'b', 'b', 'l', 'e'});
    }

    @Test
    void returnsCharArrayWithCharset(
        @TestResource(value = "utf16.txt", charset = "UTF-16") char[] actual) {
      // then
      assertThat(actual).isEqualTo(new char[] {'\u30bc', '\u30ea', '\u30fc'});
    }
  }

  @Nested
  class BoxedCharArrayResolverTest {
    @Test
    void returnsCharArray(@TestResource("wibble.txt") Character[] actual) {
      // then
      assertThat(actual).isEqualTo(new Character[] {'w', 'i', 'b', 'b', 'l', 'e'});
    }

    @Test
    void returnsCharArrayWithCharset(
        @TestResource(value = "utf16.txt", charset = "UTF-16") Character[] actual) {
      // then
      assertThat(actual).isEqualTo(new Character[] {'\u30bc', '\u30ea', '\u30fc'});
    }
  }
}
