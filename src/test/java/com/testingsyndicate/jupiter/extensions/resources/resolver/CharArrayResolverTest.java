package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.ResourcesExtension;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

class CharArrayResolverTest {

  @Nested
  @ExtendWith(ResourcesExtension.class)
  class PrimitiveCharArrayResolverTest extends AbstractResolverTest {
    @Test
    void returnsCharArray(@TestResource("wibble.txt") char[] actual) {
      // then
      assertThat(actual).isEqualTo(new char[] {'w', 'i', 'b', 'b', 'l', 'e'});
    }

    @Test
    void returnsCharArrayWithCharset(
        @TestResource(value = "utf16.txt", charset = "UTF-16") char[] actual) {
      // then
      assertThat(actual).isEqualTo(new char[] {'ゼ', 'リ', 'ー'});
    }
  }

  @Nested
  @ExtendWith(ResourcesExtension.class)
  class BoxedCharArrayResolverTest extends AbstractResolverTest {
    @Test
    void returnsCharArray(@TestResource("wibble.txt") Character[] actual) {
      // then
      assertThat(actual).isEqualTo(new Character[] {'w', 'i', 'b', 'b', 'l', 'e'});
    }

    @Test
    void returnsCharArrayWithCharset(
        @TestResource(value = "utf16.txt", charset = "UTF-16") Character[] actual) {
      // then
      assertThat(actual).isEqualTo(new Character[] {'ゼ', 'リ', 'ー'});
    }
  }
}
