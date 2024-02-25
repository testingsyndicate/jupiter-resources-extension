package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class InputStreamResolverTest {
  @Test
  void returnsInputStream(@TestResource("wibble.txt") InputStream actual) {
    // then
    assertThat(actual).hasBinaryContent(new byte[] {0x77, 0x69, 0x62, 0x62, 0x6c, 0x65});
  }
}
