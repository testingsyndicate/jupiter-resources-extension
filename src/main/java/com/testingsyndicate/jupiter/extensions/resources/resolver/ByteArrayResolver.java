package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteArrayResolver {
  public static class PrimitiveByteArrayResolver extends ResourceResolver<Object> {
    public PrimitiveByteArrayResolver() {
      super(byte[].class);
    }

    @Override
    protected byte[] doResolve(URL url, Charset charset) throws IOException {
      return toByteArray(url);
    }
  }

  public static class BoxedByteArrayResolver extends ResourceResolver<Byte[]> {
    public BoxedByteArrayResolver() {
      super(Byte[].class);
    }

    @Override
    protected Byte[] doResolve(URL url, Charset charset) throws IOException {
      byte[] primitive = toByteArray(url);
      var boxed = new Byte[primitive.length];
      Arrays.setAll(boxed, i -> primitive[i]);
      return boxed;
    }
  }

  public static class ByteArrayInputStreamResolver extends ResourceResolver<ByteArrayInputStream> {
    public ByteArrayInputStreamResolver() {
      super(ByteArrayInputStream.class);
    }

    @Override
    protected ByteArrayInputStream doResolve(URL url, Charset charset) throws IOException {
      return new ByteArrayInputStream(toByteArray(url));
    }
  }

  private static byte[] toByteArray(URL url) throws IOException {
    try (var is = url.openStream();
        var os = new ByteArrayOutputStream()) {
      is.transferTo(os);
      return os.toByteArray();
    }
  }
}
