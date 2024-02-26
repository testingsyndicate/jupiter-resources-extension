package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class FileResolver extends ResourceResolver<File> {
  public FileResolver() {
    super(File.class);
  }

  @Override
  protected File doResolve(ResolutionContext context, URL url) {
    return new File(URI.create(url.toString()));
  }
}
