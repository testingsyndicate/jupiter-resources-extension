package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver.ResolutionContext;
import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

abstract class AbstractResolverTest {
  static final URL VALID_URL = url("wibble.txt");

  static URL url(String name) {
    return AbstractResolverTest.class.getResource(name);
  }

  static void sink(Closeable closeable) {
    try {
      closeable.close();
    } catch (IOException ex) {
      // ignored
    }
  }

  ResolutionContext context() {
    return new ResolutionContext() {
      @Override
      public String name() {
        return null;
      }

      @Override
      public Class<?> sourceClass() {
        return AbstractResolverTest.this.getClass();
      }
    };
  }
}
