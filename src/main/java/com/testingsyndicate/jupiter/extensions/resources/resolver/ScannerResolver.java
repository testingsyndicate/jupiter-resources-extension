package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ScannerResolver extends ResourceResolver<Scanner> {
  public ScannerResolver() {
    super(Scanner.class, true);
  }

  @Override
  protected Scanner doResolve(URL url, Charset charset) throws IOException {
    return new Scanner(url.openStream(), charset);
  }
}
