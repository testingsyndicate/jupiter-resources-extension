# jupiter-resources-extension

Easily inject file based resources into your jupiter tests

## Coordinates

See [Releases](https://github.com/testingsyndicate/jupiter-resources-extension/releases) for the latest maven/gradle
coordinates and version

## Enabling the extension

If you are using at least junit v5.8 then you don't need to do anything to enable the extension, you can
jump straight to [Injecting resources](#injecting-resources)

<details>
  <summary>I'm using junit &lt; v5.8</summary>

  You can enable the extension on a per-test class basis using the `@ExtendWith` annotation to register
  the `ResourcesExtension` like so:

  ```java
  @ExtendWith(ResourcesExtension.class)
  class MyTests {
    // ...
  }
  ```

  Alternatively the extension also advertises itself for automatic registration, providing you have enabled
  it via setting the property `junit.jupiter.extensions.autodetection.enabled` to `true`.  How you do this
  will differ depending on your build tool.
</details>

## Injecting resources

Resources can be injected into tests through parameters on test methods, or non-static fields in test classes.  A number
of different types can be used which are [listed here](#supported-types).  The parameter which needs to be populated must be
annotated with the `@TestResource` annotation, which configures the path to the resource

```java
class MyTests {
  @Test
  void myTest(@TestResource("myFile.txt") String myFile) {
    // `myFile` will contain the contents of myFile.txt
  }

  @Test
  void myOtherTest(@TestResource("myFile.txt") InputStream inputStream) {
    // `inputStream` is reading the contents of myFile.txt
  }

  @TestResource("myFile.txt") String field;
  @Test
  void testFromField() {
    // `field` will contain the contents of myFile.txt
  }
}
```

## Specifying charset

For some types, the charset can make a difference and so this can also be specified on the annotation alongside the path.
By default, the system default charset will be used.

```java
class MyTests {
  @Test
  void myTest(@TestResource(value = "myFile.txt", charset = "UTF-8") String myFile) {
    // `myFile` will contain the contents of myFile.txt, read using UTF-8
  }
}
```

Not all types support charset, for example when reading into a `byte[]`.  If you specify a charset on an unsupported type
then the charset will be ignored.

## Choosing directory

By default, resources are loaded from the current test class path.  For example, if you are writing a test in a class
named `com.example.SomeTests` and use the annotation `@TestResource("file.txt")` then the resource will be loaded from
`/com/example/file.txt`.

You can use a `/` at the start of your filename to read from the root of the classpath instead, for example
`@TestResource("/file.txt")`.

You can also make use of the `@TestResourceDirectory` annotation to set a default directory to save specifying it
on every parameter.  This annotation can be specified on a test field, parameter, method, class, parent class or package
(in `package-info.java`).  The first annotation found in this order will be used to determine the directory.

```java
package com.example;

@TestResourceDirectory("subdir")
class SomeTests {
  @Test
  void example1(@TestResource("file.txt") String file) {
    // /com/example/subdir/file.txt
  }

  @Test
  void example2(@TestResource("/file.txt") String file) {
    // /file.txt
  }

  @Test
  @TestResourceDirectory("other")
  void example3(@TestResource("file.txt") String file) {
    // /com/example/other/file.txt
  }

  @Test
  @TestResourceDirectory("/")
  void example4(@TestResource("file.txt") String file) {
    // /file.txt
  }

  @Test
  void example5(
    @TestResource("one.txt") String one,
    @TestResourceDirectory("/other") @TestResource("two.txt") String two) {
    // /com/example/subdir/one.txt
    // /other/two.txt
  }
}
```

## ResourceInfo

A built-in type, `ResourceInfo` can also be injected, this type provides some information about the resource itself, and
also allows you to re-resolve into other types later in the test.

```java
package com.example;

class MyTests {
  @Test
  void testResourceInfo(@TestResource("file.txt") ResourceInfo info) {
    var name = info.getName(); // "file.txt"
    var fullName = info.getFullName(); // "com/example/file.txt"

    // content of file.txt as if it was injected as a string in the first place
    var content = info.resolveTo(String.class);
    
    // an InputStream reading the content of file.txt
    try (var is = info.resolveTo(InputStream.class)) {
      is.read();
    } // remember to close!
  }
}
```

Note that if you call `resolveTo` for a closeable type, it's your responsibility to close it.

## Supported Types

The following types are supported, however you can also [add your own](#adding-custom-types)!

| Type                 | Charset Supported? |
|----------------------|--------------------|
| byte[]               | No                 |
| Byte[]               | No                 |
| ByteArrayInputStream | No                 |
| char[]               | Yes                |
| CharSequence         | Yes                |
| Character[]          | Yes                |
| File                 | No                 |
| InputStream          | No                 |
| JarFile              | No                 |
| Path                 | No                 |
| ResourceInfo         | Yes*               |
| Scanner              | Yes                |
| String               | Yes                |
| StringBuffer         | Yes                |
| StringBuilder        | Yes                |
| StringReader         | Yes                |
| URI                  | No                 |
| URL                  | No                 |
| ZipFile              | Yes                |

*ResourceInfo passes charset information down when calling `ResouceInfo::resolveTo(T)`

## Adding custom types

Each type is loaded using an implementation of a `ResourceResolver<>`.  Implementations are loaded automatically
via ServiceLoader by creating a file at the path `META-INF/services/com.testingsyndicate.jupiter.extensions.resources.ResourceResolver`
and then adding a line with the fully qualified class name of your implementation.  Once you've done this just use
it like any of the built-in ones!

You should implement the `doResolve` method in your implementation.  When calling the super constructor, specify
the type this resolver supports.

If you think your resolver will be useful for others (i.e. it's for a built-in java type), then feel free to raise a
pull request to add it to the library!

## Thanks!

Thanks to [@chaosmi](https://github.com/chaosmi) for the inspiration!
