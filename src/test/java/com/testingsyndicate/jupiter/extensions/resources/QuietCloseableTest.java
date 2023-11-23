package com.testingsyndicate.jupiter.extensions.resources;

import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuietCloseableTest {

  private AutoCloseable mockCloseable;

  @BeforeEach
  void beforeEach() {
    mockCloseable = mock();
  }

  @Test
  void closesWhenClose() throws Throwable {
    // given
    var sut = new QuietCloseable(mockCloseable);

    // when
    sut.close();

    // then
    verify(mockCloseable).close();
  }

  @Test
  void swallowsWhenException() throws Throwable {
    // given
    doThrow(IOException.class).when(mockCloseable).close();
    var sut = new QuietCloseable(mockCloseable);

    // when
    sut.close();

    // then
    verify(mockCloseable).close();
  }
}
