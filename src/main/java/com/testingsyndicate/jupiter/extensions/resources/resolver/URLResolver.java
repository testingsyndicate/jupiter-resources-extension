package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.net.URL;

public class URLResolver extends ResourceResolver<URL> {
  public URLResolver() {
    super(URL.class);
  }

  @Override
  public URL doResolve(ResolutionContext context, URL url) {
    return url;
  }
}
