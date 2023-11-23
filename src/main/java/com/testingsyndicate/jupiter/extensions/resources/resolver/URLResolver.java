package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.net.URL;
import java.nio.charset.Charset;

public class URLResolver extends ResourceResolver<URL> {
  public URLResolver() {
    super(URL.class);
  }

  @Override
  public URL doResolve(URL url, Charset charset) {
    return url;
  }
}
