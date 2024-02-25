package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.ResourceInfo;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import org.junit.jupiter.api.Test;

class ResourceInfoResolverTest {
  @Test
  void returnsRelativeResourceInfo(@TestResource("wibble.txt") ResourceInfo actual) {
    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("wibble.txt");
    assertThat(actual.getFullName())
        .isEqualTo("/com/testingsyndicate/jupiter/extensions/resources/resolver/wibble.txt");
  }

  @Test
  void returnsAbsoluteResourceInfo(@TestResource("/root.txt") ResourceInfo actual) {
    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("/root.txt");
    assertThat(actual.getFullName()).isEqualTo("/root.txt");
  }
}
