package com.testingsyndicate.jupiter.extensions.resources.resolver;

import static java.util.Collections.list;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.testingsyndicate.jupiter.extensions.resources.TestResource;
import com.testingsyndicate.jupiter.extensions.resources.resolver.ArchiveResolver.JarFileResolver;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArchiveResolverTest {

  @Nested
  class ZipFileResolverTest {
    @Test
    void returnsZipFile(@TestResource("test.jar") ZipFile actual) {
      // then
      assertThat(actual.getEntry("wibble.txt")).isNotNull();
    }

    @Test
    void returnsZipFileWithCharset(
        @TestResource(value = "test.jar", charset = "UTF-8") ZipFile actual) {
      // then
      var list = list(actual.entries());
      assertThat(list).isNotEmpty();
    }
  }

  @Nested
  class JarFileResolverTest extends AbstractResolverTest {
    private static final JarFileResolver SUT = new JarFileResolver();

    @Test
    void returnsJarFile(@TestResource("test.jar") JarFile actual) {
      // then
      assertThat(actual.getJarEntry("wibble.txt")).isNotNull();
    }

    @Test
    void throwsWhenCharsetSpecified() {
      // when
      var actual = catchThrowable(() -> sink(SUT.resolve(VALID_URL, StandardCharsets.UTF_8)));

      // then
      assertThat(actual)
          .hasMessage("charset not supported for resolving instances of java.util.jar.JarFile");
    }
  }
}
