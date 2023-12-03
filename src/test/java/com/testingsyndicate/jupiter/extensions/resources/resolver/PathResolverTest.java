package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class PathResolverTest extends AbstractResolverTest {
  private static final PathResolver SUT = new PathResolver();

  @Test
  void returnsPath(@TestResource("wibble.txt") Path actual) {
    // then
    assertThat(actual.getFileName().toString()).isEqualTo("wibble.txt");
  }

  @Test
  void throwsWithCharset() {
    // when
    var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

    // then
    assertThat(actual)
        .hasMessage("charset not supported for resolving instances of java.nio.file.Path");
  }
}
