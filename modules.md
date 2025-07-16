
# Java Modules
This document explains the Java Platform Module System (JPMS) introduced in Java 9. It aims to clarify **what Java modules are**, **why they were introduced**, and **how they can be used** in modern Java applications.

## What Are Java Modules?

Java modules are a way to group related Java packages and resources together into a **named unit** with a well-defined **dependency model** and **encapsulation rules**. A module is defined using a special file called `module-info.java`.

Before Java 9, Java applications were organized using JAR files and classpaths. However, this approach lacked strict boundaries. Any class in any JAR could access any public class in another JAR, making it hard to manage large codebases. With modules, Java enforces explicit declarations of dependencies and what a module exposes to others.

why java modules:
- **Explicit dependency declarations** (`requires`), avoiding runtime surprises.
- **Strong encapsulation**: only explicitly exported packages are visible to other modules.  
- **Smaller, optimized runtime bundles**: only include necessary JDK modules. :contentReference[oaicite:1]{index=1}


## Structure of a Modular Java Project

A modular project has a specific layout to allow the compiler and runtime to properly recognize modules.

Example:

```

src/
├── com.example.app/
│   ├── module-info.java
│   └── com/example/app/Main.java
├── com.example.utils/
│   ├── module-info.java
│   └── com/example/utils/StringUtils.java

```

Each top-level directory under `src/` represents a module. The module name (e.g., `com.example.app`) should match the folder name and the declaration inside `module-info.java`. Packages inside should match standard package naming conventions.

## Understanding `module-info.java`

This file is the descriptor for a module. It specifies the module’s name, its dependencies, and what packages it makes available to other modules. It makes module boundaries explicit, helping the compiler and runtime check for missing dependencies and enforce encapsulation.


Example:

```java
module com.example.app {
    requires com.example.utils;
    exports com.example.app.api;
}
```

* `requires` declares another module this module depends on.
* `exports` makes a specific package accessible to other modules.

## Types of Modules

There are four main types of modules in Java:

1. **System modules**: Modules provided by the JDK (e.g., `java.base`, `java.sql`).
2. **Application modules**: Modules written and compiled by the user.
3. **Automatic modules**: Regular JARs placed on the module path; the module name is derived from the JAR filename.
4. **Unnamed module**: Code on the classpath belongs to the unnamed module; it can access any public class but cannot be referenced from named modules.


## Compiling and Running Modules

To compile a modular application, use the `--module-source-path` and `-d` options:

```bash
javac --module-source-path src -d out $(find src -name "*.java")
```

To run the compiled module:

```bash
java --module-path out -m com.example.app/com.example.app.Main
```

The `--module-path` replaces the traditional `-cp` classpath. The module name and main class are separated by a `/`.


## Encapsulation and Accessibility

In JPMS, packages are not accessible to other modules unless explicitly exported. This promotes encapsulation. Furthermore, reflection (e.g., used by frameworks) is restricted unless you use `opens`.

```java
opens com.example.internal to framework.module;
```

Use `opens` to allow deep reflection, for example in dependency injection or serialization libraries.


## Advanced Keywords: `transitive`, `static`

* `requires transitive`: Exposes a dependency of your module to other modules that depend on you.
* `requires static`: Indicates a compile-time-only dependency (e.g., for annotations or compile-time processors).

Example:

```java
module com.example.core {
    requires transitive com.example.api;
    requires static com.example.annotations;
}
```

## Using Services in Modules

JPMS supports the concept of service providers and consumers natively.

Example:

```java
// Provider module
module com.example.provider {
    provides com.example.api.Service with com.example.impl.ServiceImpl;
}

// Consumer module
module com.example.consumer {
    uses com.example.api.Service;
}
```

At runtime, the service is discovered via the `ServiceLoader` API.

## Packaging and Distribution

To package modules into JARs:

```bash
jar --create --file=com.example.app.jar --main-class=com.example.app.Main -C out/com.example.app .
```

To run a modular JAR:

```bash
java -p . -m com.example.app
```

You can also create a trimmed runtime image using `jlink`:

```bash
jlink --module-path $JAVA_HOME/jmods:out --add-modules com.example.app --output my-app-runtime
```


## References
- [Jenkov’s Java Modules tutorial](https://jenkov.com/tutorials/java/modules.html) 

- [Baeldung’s deep dive into Java modularity](https://www.baeldung.com/java-modularity)