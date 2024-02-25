package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class FileResolverTest extends AbstractResolverTest {
  private static final FileResolver SUT = new FileResolver();

  @Test
  void returnsFile(@TestResource("wibble.txt") File actual) {
    // then
    assertThat(actual.getName()).isEqualTo("wibble.txt");
  }

  @Test
  void throwsWithCharset() {
    // given
    var context = context();
    var url = url("wibble.txt");

    // when
    var actual = catchThrowable(() -> SUT.resolve(context, url, StandardCharsets.UTF_8));

    // then
    assertThat(actual).hasMessage("charset not supported for resolving instances of java.io.File");
  }
}
