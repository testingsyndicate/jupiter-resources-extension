package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public class URIResolver extends ResourceResolver<URI> {
  public URIResolver() {
    super(URI.class);
  }

  @Override
  protected URI doResolve(URL url, Charset charset) {
    return URI.create(url.toString());
  }
}
