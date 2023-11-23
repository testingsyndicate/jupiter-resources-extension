package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public class FileResolver extends ResourceResolver<File> {
  public FileResolver() {
    super(File.class);
  }

  @Override
  protected File doResolve(URL url, Charset charset) {
    return new File(URI.create(url.toString()));
  }
}
