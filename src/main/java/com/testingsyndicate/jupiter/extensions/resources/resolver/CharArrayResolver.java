package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver.ResolutionContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CharArrayResolver {

  public static class PrimitiveCharArrayResolver extends ResourceResolver<Object> {
    public PrimitiveCharArrayResolver() {
      super(char[].class);
    }

    @Override
    protected char[] doResolve(ResolutionContext context, URL url) throws IOException {
      return toCharArray(context, url);
    }
  }

  public static class BoxedCharArrayResolver extends ResourceResolver<Character[]> {
    public BoxedCharArrayResolver() {
      super(Character[].class);
    }

    @Override
    protected Character[] doResolve(ResolutionContext context, URL url) throws IOException {
      var primitive = toCharArray(context, url);
      var boxed = new Character[primitive.length];
      Arrays.setAll(boxed, i -> primitive[i]);
      return boxed;
    }
  }

  private static char[] toCharArray(ResolutionContext context, URL url) throws IOException {
    var charset = context.charset().orElseGet(Charset::defaultCharset);
    try (var is = url.openStream();
        var os = new ByteArrayOutputStream()) {

      is.transferTo(os);
      return os.toString(charset).toCharArray();
    }
  }
}
