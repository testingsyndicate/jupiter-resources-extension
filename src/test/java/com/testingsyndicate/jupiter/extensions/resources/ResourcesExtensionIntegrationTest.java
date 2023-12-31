package com.testingsyndicate.jupiter.extensions.resources;

import static com.testingsyndicate.jupiter.extensions.resources.TestSupport.assertFailure;
import static com.testingsyndicate.jupiter.extensions.resources.TestSupport.runTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.testingsyndicate.jupiter.extensions.resources.TestSupport.TestKitTest;
import java.nio.charset.UnsupportedCharsetException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;

class ResourcesExtensionIntegrationTest {

  @Nested
  class ParameterResolutionTest {

    @Test
    void resolvesFromRootDirectory(@TestResource("/root.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("I am ~g~root");
    }

    @Test
    void resolvesFromClassDirectory(@TestResource("class.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("class!");
    }

    @Test
    void resolvesFromSubDirectory(@TestResource("resolver/wibble.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("wibble");
    }

    @Test
    void resolvesFromRootSubDirectory(@TestResource("/directory/file.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("i'm in a directory");
    }
  }

  @Nested
  class FieldResolutionTest {

    @TestResource("/root.txt")
    String root;

    @TestResource("class.txt")
    String clazz;

    @TestResourceDirectory("resolver")
    @TestResource("wibble.txt")
    String wibble;

    @Test
    void resolvesFromRootDirectory() {
      // then
      assertThat(root).isEqualTo("I am ~g~root");
    }

    @Test
    void resolvesFromClassDirectory() {
      // then
      assertThat(clazz).isEqualTo("class!");
    }

    @Test
    void resolvesFromSpecifiedDirectory() {
      // then
      assertThat(wibble).isEqualTo("wibble");
    }
  }

  @Nested
  @TestResourceDirectory("resolver")
  class DirectoryResolutionTest {

    @Test
    void resolvesFromTestClassResourceDirectory(@TestResource("wibble.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("wibble");
    }

    @Test
    void startingSlashResolvesFromRoot(@TestResource("/root.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("I am ~g~root");
    }

    @Test
    @TestResourceDirectory("/directory")
    void resourceDirectoryOverriddenForTest(@TestResource("file.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("i'm in a directory");
    }

    @Test
    void resourceDirectoryOverriddenForParameter(
        @TestResourceDirectory("/directory") @TestResource("file.txt") String actual) {
      // then
      assertThat(actual).isEqualTo("i'm in a directory");
    }

    @Nested
    class NestedTest {

      @Test
      void takesDirectoryFromParentClassWhenNested(@TestResource("wibble.txt") String actual) {
        // then
        assertThat(actual).isEqualTo("wibble");
      }
    }
  }

  @Test
  void missingFile() {
    var results = runTest(TestStubs.class, "missingFile", String.class);

    assertFailure(ParameterResolutionException.class, results)
        .hasMessage("Unable to find resource nope.txt");
  }

  @Test
  void unsupportedType() {
    var results = runTest(TestStubs.class, "unsupportedType", Boolean.class);

    assertFailure(ParameterResolutionException.class, results)
        .hasMessage("No resolver registered for type java.lang.Boolean");
  }

  @Test
  void invalidCharset() {
    var results = runTest(TestStubs.class, "invalidCharset", String.class);

    assertFailure(ParameterResolutionException.class, results)
        .hasMessage("Unsupported charset WIBBLE-8")
        .hasCauseInstanceOf(UnsupportedCharsetException.class);
  }

  static class TestStubs {

    @TestKitTest
    void missingFile(@TestResource("nope.txt") String actual) {}

    @TestKitTest
    void unsupportedType(@TestResource("/root.txt") Boolean actual) {}

    @TestKitTest
    void invalidCharset(@TestResource(value = "/root.txt", charset = "WIBBLE-8") String actual) {}
  }
}
