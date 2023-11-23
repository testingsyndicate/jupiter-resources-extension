package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.ResourcesExtension;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ResourcesExtension.class)
class URLResolverTest extends AbstractResolverTest {
  private static final URLResolver SUT = new URLResolver();

  @Test
  void returnsURL(@TestResource("wibble.txt") URL actual) {
    // then
    assertThat(actual).hasProtocol("file");
    assertThat(actual.getPath()).endsWith("wibble.txt");
  }

  @Test
  void throwsWithCharset() {
    // when
    var actual = catchThrowable(() -> SUT.resolve(VALID_URL, StandardCharsets.UTF_8));

    // then
    assertThat(actual).hasMessage("charset not supported for resolving instances of URL");
  }
}
