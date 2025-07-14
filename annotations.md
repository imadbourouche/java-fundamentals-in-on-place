# Java Annotations - Deep Dive

This repository explores **Java Annotations**, a powerful feature of the language that adds metadata to code without affecting the logic itself. Annotations are essential for modern Java development, enabling features like compile-time checks, configuration-driven frameworks (like Spring), and reflection-based logic.

## What Are Annotations?

Annotations are **metadata** attached to Java code elements such as classes, methods, fields, and parameters. They provide information that tools, frameworks, or even the compiler can use to influence behavior, generate code, validate structure, or inject dependencies.

While they don’t directly impact the runtime execution unless processed, annotations form the backbone of many powerful libraries and APIs in Java.

## Basic Syntax

An annotation starts with the `@` symbol followed by its name:

```java
@Override
public String toString() {
    return "Custom String";
}
````

Annotations can also include **elements (attributes)**:

```java
@RequestMapping(path = "/hello", method = RequestMethod.GET)
```

If an annotation has a single element named `value`, you can omit the name:

```java
@Role("ADMIN") // instead of @Role(value = "ADMIN")
```

## Where Can You Use Annotations?

Annotations can be used on:

* Classes and interfaces
* Methods
* Fields
* Parameters
* Constructors
* Local variables
* Modules, packages, and even types (since Java 8)

This flexibility allows annotations to serve multiple roles: marking configuration, triggering validations, or describing structure.


## Common Built-in Annotations

Java ships with several commonly used annotations. Here are some important ones:

### `@Override`

Informs the compiler that the method is intended to override a superclass method. If not, it causes a compile error.

```java
@Override
public void run() {
    // implementation
}
```

### `@Deprecated`

Marks a method or class as obsolete. It still compiles, but shows warnings to users.

```java
@Deprecated
public void oldMethod() { }
```

### `@SuppressWarnings`

Instructs the compiler to ignore specific warnings.

```java
@SuppressWarnings("unchecked")
List rawList = new ArrayList();
```

###  `@Contended`
The @Contended annotation, @jdk.internal.vm.annotation.Contended, is used to help avoid False Sharing, a concurrency performance degradation problem. You can read more about false sharing in Java via the above link, and also about how the @Contended annotation works.

## Meta-Annotations

Meta-annotations are annotations **used on other annotations** to define their behavior.

### `@Retention`

Defines how long the annotation should be retained:

* `SOURCE`: Discarded during compilation.
* `CLASS`: Kept in the `.class` file, not visible at runtime.
* `RUNTIME`: Retained and accessible via reflection.

```java
@Retention(RetentionPolicy.RUNTIME)
```

### `@Target`

Specifies where the annotation can be applied:

* `TYPE` (class/interface)
* `METHOD`
* `FIELD`
* `PARAMETER`
* `CONSTRUCTOR`, etc.

```java
@Target({ElementType.METHOD, ElementType.TYPE})
```

### `@Inherited`

Indicates that a subclass will inherit the annotation from its superclass (only for class-level annotations).

### `@Documented`

Marks that the annotation should appear in the Javadoc.

## Creating Custom Annotations

You can define your own annotations to represent domain-specific behavior or metadata.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
    String tableName();
}
```

### Notes:

* Elements are defined as methods inside the annotation.
* Return types must be primitives, `String`, `Class`, `enum`, other annotations, or arrays of these.
* Default values can be assigned using `default`.

```java
public @interface Role {
    String value() default "USER";
}
```

## Accessing Annotations via Reflection

To utilize annotations at runtime, Java provides the Reflection API. Only annotations with `RUNTIME` retention are available this way.

Example:

```java
@Role("ADMIN")
public class AdminService { }

if (AdminService.class.isAnnotationPresent(Role.class)) {
    Role role = AdminService.class.getAnnotation(Role.class);
    System.out.println("Role: " + role.value());
}
```

This is commonly used in frameworks to apply behavior based on metadata.

## Practical Use Cases

* **Dependency Injection** (e.g., `@Autowired` in Spring)
* **Routing** in web frameworks (e.g., `@GetMapping`)
* **Validation** (e.g., `@NotNull`, `@Size`)
* **Testing** (e.g., `@Test` in JUnit)
* **Entity Mapping** (e.g., `@Entity`, `@Id` in JPA)

Annotations make code cleaner, remove boilerplate, and support declarative programming styles.

## Sources
- [Jenkov’s  Java Annotations tutorial](https://jenkov.com/tutorials/java/annotations.html)