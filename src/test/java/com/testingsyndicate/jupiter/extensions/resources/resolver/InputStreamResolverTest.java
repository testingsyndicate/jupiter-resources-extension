package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class InputStreamResolverTest extends AbstractResolverTest {
  private static final InputStreamResolver SUT = new InputStreamResolver();

  @Test
  void returnsInputStream(@TestResource("wibble.txt") InputStream actual) {
    // then
    assertThat(actual).hasBinaryContent(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
  }

  @Test
  void throwsWithCharset() {
    // given
    var context = context();

    // when
    var actual =
        catchThrowable(() -> sink(SUT.resolve(context, VALID_URL, StandardCharsets.UTF_8)));

    // then
    assertThat(actual)
        .hasMessage("charset not supported for resolving instances of java.io.InputStream");
  }
}
