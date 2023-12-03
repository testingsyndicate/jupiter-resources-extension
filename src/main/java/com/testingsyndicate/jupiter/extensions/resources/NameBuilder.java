package com.testingsyndicate.jupiter.extensions.resources;

class NameBuilder {
  private static final String SEPARATOR = "/";
  private final StringBuilder name = new StringBuilder();

  NameBuilder() {}

  public NameBuilder append(String part) {
    if (part == null) {
      return this;
    }
    if (part.startsWith(SEPARATOR) || name.isEmpty()) {
      name.replace(0, name.length(), part);
      return this;
    }
    if (!SEPARATOR.equals(name.substring(name.length() - SEPARATOR.length()))) {
      name.append(SEPARATOR);
    }
    name.append(part);

    return this;
  }

  public String build() {
    return name.toString();
  }
}
