package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.net.URI;
import org.junit.jupiter.api.Test;

class URIResolverTest {
  @Test
  void returnsURI(@TestResource("wibble.txt") URI actual) {
    // then
    assertThat(actual).hasScheme("file");
    assertThat(actual.getPath()).endsWith("wibble.txt");
  }
}
