package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.NameBuilder;
import com.testingsyndicate.jupiter.extensions.resources.ResourceInfo;
import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class ResourceInfoResolver extends ResourceResolver<ResourceInfo> {
  public ResourceInfoResolver() {
    super(ResourceInfo.class, false);
  }

  @Override
  protected ResourceInfo doResolve(ResolutionContext context, URL url, Charset charset)
      throws IOException {
    var name = context.name();
    var fullName = fullName(context.sourceClass(), name);

    return new ResolvedResourceInfo(name, fullName);
  }

  private static String fullName(Class<?> clazz, String name) {
    var builder = new NameBuilder().append("/");
    var pkg = clazz.getPackageName();
    for (var part : pkg.split("\\.")) {
      builder.append(part);
    }
    return builder.append(name).build();
  }

  private static class ResolvedResourceInfo implements ResourceInfo {
    private final String name;
    private final String fullName;

    private ResolvedResourceInfo(String name, String fullName) {
      this.name = name;
      this.fullName = fullName;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getFullName() {
      return fullName;
    }
  }
}
