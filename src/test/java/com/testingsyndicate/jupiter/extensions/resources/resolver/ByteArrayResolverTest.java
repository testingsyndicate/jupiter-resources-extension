package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ByteArrayResolverTest {

  @Nested
  class PrimitiveByteArrayResolverTest {
    @Test
    void returnsByteArray(@TestResource("wibble.txt") byte[] actual) {
      // then
      assertThat(actual).isEqualTo(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }
  }

  @Nested
  class BoxedByteArrayResolverTest {
    @Test
    void returnsByteArray(@TestResource("wibble.txt") Byte[] actual) {
      // then
      assertThat(actual).isEqualTo(new Byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }
  }

  @Nested
  class ByteArrayInputStreamResolverTest {
    @Test
    void returnsByteArrayInputStream(@TestResource("wibble.txt") ByteArrayInputStream actual) {
      // then
      assertThat(actual.readAllBytes()).isEqualTo(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }
  }
}
