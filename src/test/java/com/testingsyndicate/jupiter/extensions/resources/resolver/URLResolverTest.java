package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.net.URL;
import org.junit.jupiter.api.Test;

class URLResolverTest {
  @Test
  void returnsURL(@TestResource("wibble.txt") URL actual) {
    // then
    assertThat(actual).hasProtocol("file");
    assertThat(actual.getPath()).endsWith("wibble.txt");
  }
}
