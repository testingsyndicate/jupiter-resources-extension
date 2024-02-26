package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;

public class StringResolver extends ResourceResolver<String> {
  public StringResolver() {
    super(String.class);
  }

  @Override
  public String doResolve(ResolutionContext context, URL url) throws IOException {
    return toString(context, url);
  }

  private static String toString(ResolutionContext context, URL url) throws IOException {
    var charset = context.charset().orElseGet(Charset::defaultCharset);
    try (var is = url.openStream();
        var os = new ByteArrayOutputStream()) {
      is.transferTo(os);
      return os.toString(charset);
    }
  }

  public static class CharSequenceResolver extends ResourceResolver<CharSequence> {
    public CharSequenceResolver() {
      super(CharSequence.class);
    }

    @Override
    protected CharSequence doResolve(ResolutionContext context, URL url) throws IOException {
      return StringResolver.toString(context, url);
    }
  }

  public static class StringBuilderResolver extends ResourceResolver<StringBuilder> {
    public StringBuilderResolver() {
      super(StringBuilder.class);
    }

    @Override
    protected StringBuilder doResolve(ResolutionContext context, URL url) throws IOException {
      return new StringBuilder(StringResolver.toString(context, url));
    }
  }

  public static class StringBufferResolver extends ResourceResolver<StringBuffer> {
    public StringBufferResolver() {
      super(StringBuffer.class);
    }

    @Override
    protected StringBuffer doResolve(ResolutionContext context, URL url) throws IOException {
      return new StringBuffer(StringResolver.toString(context, url));
    }
  }

  public static class StringReaderResolver extends ResourceResolver<StringReader> {
    public StringReaderResolver() {
      super(StringReader.class);
    }

    @Override
    protected StringReader doResolve(ResolutionContext context, URL url) throws IOException {
      return new StringReader(StringResolver.toString(context, url));
    }
  }
}
