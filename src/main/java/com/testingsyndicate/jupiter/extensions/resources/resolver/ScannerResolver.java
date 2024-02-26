package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ScannerResolver extends ResourceResolver<Scanner> {
  public ScannerResolver() {
    super(Scanner.class);
  }

  @Override
  protected Scanner doResolve(ResolutionContext context, URL url) throws IOException {
    var charset = context.charset().orElseGet(Charset::defaultCharset);
    return new Scanner(url.openStream(), charset);
  }
}
