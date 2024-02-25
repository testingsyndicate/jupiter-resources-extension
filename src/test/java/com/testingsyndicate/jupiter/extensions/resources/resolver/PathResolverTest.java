package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class PathResolverTest {
  @Test
  void returnsPath(@TestResource("wibble.txt") Path actual) {
    // then
    assertThat(actual.getFileName().toString()).isEqualTo("wibble.txt");
  }
}
