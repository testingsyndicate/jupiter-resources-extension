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

/**
 * Resolver of a specific resource type. Implementations of this class are located using
 * ServiceLoader and should be added to a relevant services file to be discovered.
 *
 * @param <T> type of the resource this resolves
 */
public abstract class ResourceResolver<T> {
  private static final Map<Class<?>, ResourceResolver<?>> RESOLVERS;

  private final Class<? extends T> target;

  public static <T> Optional<ResourceResolver<T>> getResolver(Class<T> target) {
    // noinspection unchecked
    var resolver = (ResourceResolver<T>) RESOLVERS.get(target);

    return Optional.ofNullable(resolver);
  }

  protected ResourceResolver(Class<? extends T> target) {
    this.target = target;
  }

  public Class<? extends T> getTarget() {
    return target;
  }

  public final T resolve(ResolutionContext context, URL url) {
    try {
      return doResolve(context, url);
    } catch (IOException iox) {
      throw new UncheckedIOException(iox);
    }
  }

  protected abstract T doResolve(ResolutionContext context, URL url) throws IOException;

  static {
    RESOLVERS =
        ServiceLoader.load(ResourceResolver.class).stream()
            .map((Provider<ResourceResolver> provider) -> (ResourceResolver<?>) provider.get())
            .collect(
                toUnmodifiableMap(
                    resolver -> (Class<?>) resolver.getTarget(),
                    resolver -> (ResourceResolver<?>) resolver));
  }

  public interface ResolutionContext {
    String name();

    Class<?> sourceClass();

    Optional<Charset> charset();
  }
}
