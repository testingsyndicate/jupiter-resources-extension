package com.testingsyndicate.jupiter.extensions.resources;

import java.io.IOException;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;

class QuietCloseable implements CloseableResource {
  private final AutoCloseable closeable;

  QuietCloseable(AutoCloseable closeable) {
    this.closeable = closeable;
  }

  @Override
  public void close() throws Throwable {
    try {
      closeable.close();
    } catch (IOException ex) {
      // ignored
    }
  }
}
