package com.testingsyndicate.jupiter.extensions.resources;

/**
 * A type which represents a resource.
 *
 * <p>Resolving test resources to this type allows you to lookup information about them, e.g. the
 * full name of the resource given the context of the class you are looking up from.
 *
 * <p>You can also re-resolve this into another type using the {@link #resolveTo(Class)} method
 */
public interface ResourceInfo {
  /**
   * @return the name of the resource requested
   */
  String getName();

  /**
   * @return the full name of the resource considering the package name
   */
  String getFullName();

  /**
   * Re-Resolve this ResourceInfo into another supported type.
   *
   * @param target the type that this resource should be resolved into
   * @return the resolved resource as an instance of the target type
   * @param <T> generic type specifier for the target type
   */
  <T> T resolveTo(Class<T> target);
}
