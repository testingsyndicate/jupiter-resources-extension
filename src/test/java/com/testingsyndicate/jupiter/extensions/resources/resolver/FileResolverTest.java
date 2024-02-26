package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.File;
import org.junit.jupiter.api.Test;

class FileResolverTest {
  @Test
  void returnsFile(@TestResource("wibble.txt") File actual) {
    // then
    assertThat(actual.getName()).isEqualTo("wibble.txt");
  }
}
