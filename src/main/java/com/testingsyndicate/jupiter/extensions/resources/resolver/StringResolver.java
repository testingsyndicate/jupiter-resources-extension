package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

public class StringResolver extends ResourceResolver<String> {
  public StringResolver() {
    super(String.class, true);
  }

  @Override
  public String doResolve(URL url, Charset charset) throws IOException {
    return toString(url, charset);
  }

  private static String toString(URL url, Charset charset) throws IOException {
    try (var is = url.openStream();
        var os = new ByteArrayOutputStream()) {
      is.transferTo(os);
      return os.toString(charset);
    }
  }

  public static class CharSequenceResolver extends ResourceResolver<CharSequence> {
    public CharSequenceResolver() {
      super(CharSequence.class, true);
    }

    @Override
    protected CharSequence doResolve(URL url, Charset charset) throws IOException {
      return StringResolver.toString(url, charset);
    }
  }

  public static class StringBuilderResolver extends ResourceResolver<StringBuilder> {
    public StringBuilderResolver() {
      super(StringBuilder.class, true);
    }

    @Override
    protected StringBuilder doResolve(URL url, Charset charset) throws IOException {
      return new StringBuilder(StringResolver.toString(url, charset));
    }
  }

  public static class StringBufferResolver extends ResourceResolver<StringBuffer> {
    public StringBufferResolver() {
      super(StringBuffer.class, true);
    }

    @Override
    protected StringBuffer doResolve(URL url, Charset charset) throws IOException {
      return new StringBuffer(StringResolver.toString(url, charset));
    }
  }

  public static class StringReaderResolver extends ResourceResolver<StringReader> {
    public StringReaderResolver() {
      super(StringReader.class, true);
    }

    @Override
    protected StringReader doResolve(URL url, Charset charset) throws IOException {
      return new StringReader(StringResolver.toString(url, charset));
    }
  }
}
