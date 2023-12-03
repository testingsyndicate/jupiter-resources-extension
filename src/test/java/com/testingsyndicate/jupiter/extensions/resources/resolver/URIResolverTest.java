package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class URIResolverTest extends AbstractResolverTest {
  private static final URIResolver SUT = new URIResolver();

  @Test
  void returnsURI(@TestResource("wibble.txt") URI actual) {
    // then
    assertThat(actual).hasScheme("file");
    assertThat(actual.getPath()).endsWith("wibble.txt");
  }

  @Test
  void throwsWithCharset() {
    // when
    var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

    // then
    assertThat(actual).hasMessage("charset not supported for resolving instances of java.net.URI");
  }
}
