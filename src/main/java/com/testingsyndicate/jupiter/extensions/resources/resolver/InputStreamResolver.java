package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class InputStreamResolver extends ResourceResolver<InputStream> {
  public InputStreamResolver() {
    super(InputStream.class);
  }

  @Override
  protected InputStream doResolve(URL url, Charset charset) throws IOException {
    return url.openStream();
  }
}
