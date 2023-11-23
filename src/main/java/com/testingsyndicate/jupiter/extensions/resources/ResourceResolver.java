package com.testingsyndicate.jupiter.extensions.resources;

import static java.util.stream.Collectors.toUnmodifiableMap;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

public abstract class ResourceResolver<T> {
  private static final Map<Class<?>, ResourceResolver<?>> RESOLVERS;

  private final Class<? extends T> target;
  private final boolean charsetSupported;

  public static <T> Optional<ResourceResolver<T>> getResolver(Class<T> target) {
    // noinspection unchecked
    var resolver = (ResourceResolver<T>) RESOLVERS.get(target);

    return Optional.ofNullable(resolver);
  }

  protected ResourceResolver(Class<? extends T> target) {
    this(target, false);
  }

  protected ResourceResolver(Class<? extends T> target, boolean charsetSupported) {
    this.target = target;
    this.charsetSupported = charsetSupported;
  }

  public Class<? extends T> getTarget() {
    return target;
  }

  public final T resolve(URL url, Charset charset) {
    try {
      if (charsetSupported) {
        if (charset == null) {
          charset = Charset.defaultCharset();
        }
      } else if (charset != null) {
        throw new RuntimeException(
            "charset not supported for resolving instances of " + target.getSimpleName());
      }
      return doResolve(url, charset);
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  protected abstract T doResolve(URL url, Charset charset) throws IOException;

  static {
    RESOLVERS =
        ServiceLoader.load(ResourceResolver.class).stream()
            .map((Provider<ResourceResolver> provider) -> (ResourceResolver<?>) provider.get())
            .collect(
                toUnmodifiableMap(
                    resolver -> (Class<?>) resolver.getTarget(),
                    resolver -> (ResourceResolver<?>) resolver));
  }
}
