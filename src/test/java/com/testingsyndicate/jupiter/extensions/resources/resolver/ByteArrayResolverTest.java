package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.ResourcesExtension;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import com.testingsyndicate.jupiter.extensions.resources.resolver.ByteArrayResolver.BoxedByteArrayResolver;
import com.testingsyndicate.jupiter.extensions.resources.resolver.ByteArrayResolver.ByteArrayInputStreamResolver;
import com.testingsyndicate.jupiter.extensions.resources.resolver.ByteArrayResolver.PrimitiveByteArrayResolver;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

class ByteArrayResolverTest {

  @Nested
  @ExtendWith(ResourcesExtension.class)
  class PrimitiveByteArrayResolverTest extends AbstractResolverTest {
    private static final PrimitiveByteArrayResolver SUT = new PrimitiveByteArrayResolver();

    @Test
    void returnsByteArray(@TestResource("wibble.txt") byte[] actual) {
      // then
      assertThat(actual).isEqualTo(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }

    @Test
    void throwsWithCharset() {
      // when
      var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

      // then
      assertThat(actual).hasMessage("charset not supported for resolving instances of byte[]");
    }
  }

  @Nested
  @ExtendWith(ResourcesExtension.class)
  class BoxedByteArrayResolverTest extends AbstractResolverTest {
    private static final BoxedByteArrayResolver SUT = new BoxedByteArrayResolver();

    @Test
    void returnsByteArray(@TestResource("wibble.txt") Byte[] actual) {
      // then
      assertThat(actual).isEqualTo(new Byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }

    @Test
    void throwsWithCharset() {
      // when
      var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

      // then
      assertThat(actual)
          .hasMessage("charset not supported for resolving instances of java.lang.Byte[]");
    }
  }

  @Nested
  @ExtendWith(ResourcesExtension.class)
  class ByteArrayInputStreamResolverTest extends AbstractResolverTest {
    private static final ByteArrayInputStreamResolver SUT = new ByteArrayInputStreamResolver();

    @Test
    void returnsByteArrayInputStream(@TestResource("wibble.txt") ByteArrayInputStream actual) {
      // then
      assertThat(actual.readAllBytes()).isEqualTo(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
    }

    @Test
    void throwsWithCharset() {
      // when
      var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

      // then
      assertThat(actual)
          .hasMessage(
              "charset not supported for resolving instances of java.io.ByteArrayInputStream");
    }
  }
}
