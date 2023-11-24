package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class ArchiveResolver {
  private static File toFile(URL url) {
    return new File(URI.create(url.toString()));
  }

  public static class ZipFileResolver extends ResourceResolver<ZipFile> {
    public ZipFileResolver() {
      super(ZipFile.class, true);
    }

    @Override
    protected ZipFile doResolve(URL url, Charset charset) throws IOException {
      return new ZipFile(toFile(url), charset);
    }
  }

  public static class JarFileResolver extends ResourceResolver<JarFile> {
    public JarFileResolver() {
      super(JarFile.class, false);
    }

    @Override
    protected JarFile doResolve(URL url, Charset charset) throws IOException {
      return new JarFile(toFile(url));
    }
  }
}
