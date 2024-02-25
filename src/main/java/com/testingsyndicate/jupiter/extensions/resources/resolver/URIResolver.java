package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.net.URI;
import java.net.URL;

public class URIResolver extends ResourceResolver<URI> {
  public URIResolver() {
    super(URI.class);
  }

  @Override
  protected URI doResolve(ResolutionContext context, URL url) {
    return URI.create(url.toString());
  }
}
