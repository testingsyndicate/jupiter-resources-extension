package com.testingsyndicate.jupiter.extensions.resources;

import com.testingsyndicate.jupiter.extensions.resources.ResourceResolver.ResolutionContext;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Jupiter extension to resolve resources into parameters in tests
 *
 * <pre> <code>
 * {@literal @}ExtendWith(ResourceExtension.class)
 * class SomeTests {
 *   {@literal @}Test
 *   void myTest({@literal @}TestResource("file.txt") String contents) {
 *     // ...
 *   }
 * }
 * </code> </pre>
 */
public class ResourcesExtension implements Extension, ParameterResolver, BeforeEachCallback {
  private static final Namespace NAMESPACE = Namespace.create(ResourcesExtension.class);

  @Override
  public boolean supportsParameter(ParameterContext parameter, ExtensionContext extension)
      throws ParameterResolutionException {
    return parameter.isAnnotated(TestResource.class);
  }

  @Override
  public Object resolveParameter(ParameterContext context, ExtensionContext extension)
      throws ParameterResolutionException {

    var parameter = context.getParameter();
    var annotation = parameter.getAnnotation(TestResource.class);
    var directory = Annotations.findHierarchical(parameter, TestResourceDirectory.class);
    var clazz = parameter.getDeclaringExecutable().getDeclaringClass();

    return resolveResource(extension, annotation, directory, parameter.getType(), clazz);
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    var clazz = context.getRequiredTestClass();
    var instance = context.getRequiredTestInstance();
    for (var result : Annotations.findAnnotatedFields(clazz, TestResource.class)) {
      var field = result.field();
      var directory = Annotations.findHierarchical(field, TestResourceDirectory.class);
      var type = field.getType();
      var value = resolveResource(context, result.annotation(), directory, type, clazz);
      field.setAccessible(true);
      field.set(instance, value);
    }
  }

  private static Object resolveResource(
      ExtensionContext context,
      TestResource annotation,
      TestResourceDirectory directory,
      Class<?> target,
      Class<?> clazz) {
    var resolver = findResolver(target);
    var name =
        new NameBuilder()
            .append(directory == null ? null : directory.value())
            .append(annotation.value())
            .build();
    var charset = resolveCharset(annotation.charset());
    var resolutionContext = new ExtensionResolutionContext(name, clazz, charset);

    var url = clazz.getResource(name);
    if (url == null) {
      throw new ParameterResolutionException("Unable to find resource %s".formatted(name));
    }

    var resource = resolver.resolve(resolutionContext, url);
    registerResource(context, resource);
    return resource;
  }

  private static Optional<Charset> resolveCharset(String name) {
    if (name == null || name.isEmpty()) {
      return Optional.empty();
    }
    try {
      var charset = Charset.forName(name);
      return Optional.of(charset);
    } catch (UnsupportedCharsetException ex) {
      throw new ParameterResolutionException("Unsupported charset " + name, ex);
    }
  }

  private static ResourceResolver<?> findResolver(Class<?> target) {
    return ResourceResolver.getResolver(target)
        .orElseThrow(
            () ->
                new ParameterResolutionException(
                    "No resolver registered for type " + target.getTypeName()));
  }

  private static void registerResource(ExtensionContext context, Object resource) {
    if (resource instanceof AutoCloseable closeable) {
      context.getStore(NAMESPACE).put(resource, new QuietCloseable(closeable));
    }
  }

  private record ExtensionResolutionContext(
      String name, Class<?> sourceClass, Optional<Charset> charset) implements ResolutionContext {}
}
