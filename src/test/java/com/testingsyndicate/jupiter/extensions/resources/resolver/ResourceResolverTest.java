package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;

class ResourceResolverTest {

  @Test
  void throwsUncheckedWhenResolverThrows() {
    // given
    var cause = new IOException();
    var sut = new TestResolver(cause);

    // when
    var actual = catchThrowable(() -> sut.resolve(null, null, null));

    // then
    assertThat(actual).isInstanceOf(UncheckedIOException.class).hasCauseReference(cause);
  }

  private static class TestResolver extends ResourceResolver<String> {
    private final IOException ex;

    TestResolver(IOException ex) {
      super(String.class);
      this.ex = ex;
    }

    @Override
    protected String doResolve(URL url, Charset charset) throws IOException {
      throw ex;
    }
  }
}
