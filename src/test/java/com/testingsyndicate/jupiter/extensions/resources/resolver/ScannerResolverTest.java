package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.ResourcesExtension;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ResourcesExtension.class)
class ScannerResolverTest extends AbstractResolverTest {

  @Test
  void returnsScanner(@TestResource("wibble.txt") Scanner actual) {
    // then
    assertThat(actual.nextLine()).isEqualTo("wibble");
  }

  @Test
  void returnsScannerWithCharset(
      @TestResource(value = "utf16.txt", charset = "UTF-16") Scanner actual) {
    // then
    assertThat(actual.nextLine()).isEqualTo("ゼリー");
  }
}
