package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InputStreamResolver extends ResourceResolver<InputStream> {
  public InputStreamResolver() {
    super(InputStream.class);
  }

  @Override
  protected InputStream doResolve(ResolutionContext context, URL url) throws IOException {
    return url.openStream();
  }
}
