package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CharArrayResolver {

  public static class PrimitiveCharArrayResolver extends ResourceResolver<Object> {
    public PrimitiveCharArrayResolver() {
      super(char[].class, true);
    }

    @Override
    protected char[] doResolve(URL url, Charset charset) throws IOException {
      return toCharArray(url, charset);
    }
  }

  public static class BoxedCharArrayResolver extends ResourceResolver<Character[]> {
    public BoxedCharArrayResolver() {
      super(Character[].class, true);
    }

    @Override
    protected Character[] doResolve(URL url, Charset charset) throws IOException {
      var primitive = toCharArray(url, charset);
      var boxed = new Character[primitive.length];
      Arrays.setAll(boxed, i -> primitive[i]);
      return boxed;
    }
  }

  private static char[] toCharArray(URL url, Charset charset) throws IOException {
    try (var is = url.openStream();
        var os = new ByteArrayOutputStream()) {
      is.transferTo(os);

      return os.toString(charset).toCharArray();
    }
  }
}
