package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.ResourceInfo;
import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class ResourceInfoResolverTest extends AbstractResolverTest {
  private static final ResourceResolver<ResourceInfo> SUT = new ResourceInfoResolver();

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
  void throwsWhenCharset() {
    // given
    var context = context();

    // when
    var actual = catchThrowable(() -> SUT.resolve(context, VALID_URL, StandardCharsets.UTF_8));

    // then
    assertThat(actual)
        .hasMessage(
            "charset not supported for resolving instances of com.testingsyndicate.jupiter.extensions.resources.ResourceInfo");
  }
}
