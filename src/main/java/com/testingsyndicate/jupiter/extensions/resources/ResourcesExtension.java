package com.testingsyndicate.jupiter.extensions.resources;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.platform.commons.support.SearchOption;

public class ResourcesExtension implements Extension, ParameterResolver {
  private static final String SEPARATOR = "/";
  private static final Namespace NAMESPACE = Namespace.create(ResourcesExtension.class);

  @Override
  public boolean supportsParameter(ParameterContext parameter, ExtensionContext extension)
      throws ParameterResolutionException {
    return parameter.isAnnotated(TestResource.class);
  }

  @Override
  public Object resolveParameter(ParameterContext context, ExtensionContext extension)
      throws ParameterResolutionException {

    var annotation = findAnnotation(context.getParameter(), TestResource.class).orElseThrow();
    var path = fullPath(context, annotation.value());
    var clazz = findDeclaration(context);

    var resolver = findResolver(context);
    var url = clazz.getResource(path);

    if (url == null) {
      throw new ParameterResolutionException("Unable to find resource %s".formatted(path));
    }

    var charset = resolveCharset(annotation.charset());

    var resource = resolver.resolve(url, charset);
    registerResource(extension, resource);
    return resource;
  }

  private static Charset resolveCharset(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    try {
      return Charset.forName(name);
    } catch (UnsupportedCharsetException ex) {
      throw new ParameterResolutionException("Unsupported charset " + name, ex);
    }
  }

  private static Optional<TestResourceDirectory> findDirectory(ParameterContext context) {
    var annotation = findAnnotation(context.getParameter(), TestResourceDirectory.class);
    if (annotation.isPresent()) {
      return annotation;
    }

    var executable = context.getDeclaringExecutable();
    annotation = findAnnotation(executable, TestResourceDirectory.class);
    if (annotation.isPresent()) {
      return annotation;
    }

    var clazz = executable.getDeclaringClass();
    return findAnnotation(
        clazz, TestResourceDirectory.class, SearchOption.INCLUDE_ENCLOSING_CLASSES);
  }

  private static ResourceResolver<?> findResolver(ParameterContext context) {
    var parameter = context.getParameter();
    var target = parameter.getType();
    return ResourceResolver.getResolver(target)
        .orElseThrow(
            () -> new ParameterResolutionException("No resolver registered for type " + target));
  }

  private static String fullPath(ParameterContext context, String name) {
    var directory = findDirectory(context);
    if (name.startsWith(SEPARATOR) || directory.isEmpty()) {
      return name;
    }

    var path = directory.get().value();
    var builder = new StringBuilder(path);
    if (!path.endsWith(SEPARATOR)) {
      builder.append(SEPARATOR);
    }
    builder.append(name);

    return builder.toString();
  }

  private static void registerResource(ExtensionContext context, Object resource) {
    if (resource instanceof AutoCloseable closeable) {
      context.getStore(NAMESPACE).put(resource, new QuietCloseable(closeable));
    }
  }

  private static Class<?> findDeclaration(ParameterContext context) {
    return context.getParameter().getDeclaringExecutable().getDeclaringClass();
  }
}
