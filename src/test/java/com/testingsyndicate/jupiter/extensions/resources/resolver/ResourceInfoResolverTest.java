package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

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

  @Test
  void returnsSelfWhenResolveToResourceInfo(@TestResource("wibble.txt") ResourceInfo info) {
    // when
    var actual = info.resolveTo(ResourceInfo.class);

    // then
    assertThat(actual).isSameAs(info);
  }

  @Test
  void resolvesWhenResolveToOther(
      @TestResource("wibble.txt") ResourceInfo info, @TestResource("wibble.txt") String expected) {
    // when
    var actual = info.resolveTo(String.class);

    // then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void throwsWhenResolveToUnsupportedType(@TestResource("wibble.txt") ResourceInfo info) {
    // when
    var actual = catchThrowable(() -> info.resolveTo(Class.class));

    // then
    assertThat(actual).hasMessage("No resolver registered for type java.lang.Class");
  }
}
