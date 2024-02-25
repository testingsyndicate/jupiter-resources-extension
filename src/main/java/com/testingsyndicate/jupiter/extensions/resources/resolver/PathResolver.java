package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class PathResolver extends ResourceResolver<Path> {
  public PathResolver() {
    super(Path.class);
  }

  @Override
  protected Path doResolve(ResolutionContext context, URL url) {
    return Path.of(URI.create(url.toString()));
  }
}
