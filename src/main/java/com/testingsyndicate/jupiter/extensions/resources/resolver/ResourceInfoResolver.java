package com.testingsyndicate.jupiter.extensions.resources.resolver;

import com.testingsyndicate.jupiter.extensions.resources.NameBuilder;
import com.testingsyndicate.jupiter.extensions.resources.ResourceInfo;
import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver;
import java.io.IOException;
import java.net.URL;

public class ResourceInfoResolver extends ResourceResolver<ResourceInfo> {
  public ResourceInfoResolver() {
    super(ResourceInfo.class);
  }

  @Override
  protected ResourceInfo doResolve(ResolutionContext context, URL url) throws IOException {
    return new ResolvedResourceInfo(context, url);
  }

  private static class ResolvedResourceInfo implements ResourceInfo {
    private final ResolutionContext context;
    private final URL url;

    private ResolvedResourceInfo(ResolutionContext context, URL url) {
      this.context = context;
      this.url = url;
    }

    @Override
    public String getName() {
      return context.name();
    }

    @Override
    public String getFullName() {
      var builder = new NameBuilder().append("/");
      var clazz = context.sourceClass();
      var pkg = clazz.getPackageName();
      for (var part : pkg.split("\\.")) {
        builder.append(part);
      }
      return builder.append(context.name()).build();
    }

    @Override
    public <T> T resolveTo(Class<T> target) {
      // check if we can return as-is to avoid nesting
      if (target.isAssignableFrom(getClass())) {
        return target.cast(this);
      }
      var resolver =
          ResourceResolver.getResolver(target)
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "No resolver registered for type " + target.getTypeName()));

      return resolver.resolve(context, url);
    }
  }
}
