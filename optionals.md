# Java Optionals

Java `Optional<T>` is a container object introduced in Java 8, used to represent the presence or absence of a value in a more expressive and less error-prone way than using `null`.

## Why Optional?

Traditionally, Java methods return `null` to indicate absence. This leads to `NullPointerException` (NPE) if the caller forgets to check. `Optional` encourages better handling of such cases by making the potential absence explicit and avoiding `null`.

## Creating Optionals

```java
Optional<String> empty = Optional.empty(); // empty optional

Optional<String> name = Optional.of("John"); // value must not be null

Optional<String> nullableName = Optional.ofNullable(getName()); // value may be null
````

Use `Optional.of()` only when you're sure the value is not null. Use `Optional.ofNullable()` to safely wrap potentially null values.

## Checking Presence

```java
Optional<String> name = Optional.of("Alice");

if (name.isPresent()) {
    System.out.println("Name: " + name.get());
}
```

This is valid but discouraged. Instead, prefer functional approaches like `ifPresent`.

## ifPresent and ifPresentOrElse

```java
Optional<String> name = Optional.of("Alice");

name.ifPresent(n -> System.out.println("Name: " + n));
```

Java 9 added `ifPresentOrElse`:

```java
name.ifPresentOrElse(
    n -> System.out.println("Name: " + n),
    () -> System.out.println("Name is missing")
);
```

## get() Method

The `get()` method returns the value if present, but throws `NoSuchElementException` if the optional is empty. Its use is discouraged unless guarded by `isPresent()`.

```java
Optional<String> name = Optional.of("Alice");
String value = name.get(); // safe here, but not recommended
```

## orElse and orElseGet

`orElse` provides a fallback value if empty:

```java
String result = Optional.ofNullable(null).orElse("default");
```

`orElseGet` uses a supplier, evaluated only when needed:

```java
String result = Optional.ofNullable(null).orElseGet(() -> computeDefault());
```

### orElse vs orElseGet Timing

```java
String eager = Optional.of("value").orElse(getFallback());     // getFallback() runs anyway
String lazy = Optional.of("value").orElseGet(() -> getFallback()); // getFallback() never runs
```

## orElseThrow

Throws an exception if the value is absent:

```java
String value = Optional.ofNullable(null)
    .orElseThrow(() -> new IllegalArgumentException("Missing value"));
```

You can use custom exceptions or standard ones like `NoSuchElementException`.

## map and flatMap

Use `map()` to transform the contained value:

```java
Optional<String> name = Optional.of("Alice");
Optional<Integer> length = name.map(String::length); // Optional[5]
```

Use `flatMap()` when your mapper returns an `Optional` itself:

```java
Optional<String> name = Optional.of("Alice");
Optional<String> upper = name.flatMap(n -> Optional.of(n.toUpperCase()));
```

## filter

Filters the value by a predicate:

```java
Optional<String> name = Optional.of("Alice");
Optional<String> filtered = name.filter(n -> n.startsWith("A")); // Optional[Alice]
```

If the predicate fails, it becomes `Optional.empty()`.

## Optional Chaining and Nested Objects

```java
Optional<User> user = getUser();

String city = user
    .flatMap(User::getAddress)
    .flatMap(Address::getCity)
    .orElse("Unknown");
```

This avoids deep null checks like:

```java
if (user != null && user.getAddress() != null && user.getAddress().getCity() != null)
```

## Using Optional in Streams

```java
List<String> names = List.of("Alice", "Bob", "", "Charlie");

List<String> result = names.stream()
    .map(String::trim)
    .map(Optional::ofNullable)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
```

Alternatively, using `flatMap` for unwrapping:

```java
Stream.of("A", null, "C")
    .map(Optional::ofNullable)
    .flatMap(Optional::stream)
    .forEach(System.out::println);
```

## Integration with Legacy Code

If you're dealing with APIs or libraries that return `null`, wrap results with `Optional.ofNullable(...)` to avoid leaks into your codebase.

```java
Optional<String> safe = Optional.ofNullable(legacyService.getValue());
```

## Performance Considerations

* `Optional` introduces object allocation overhead.
* Avoid using `Optional` in fields, method parameters, or constructors in performance-critical code.
* Don’t use it in data transfer objects (DTOs), entities, or where serialization is required.

## Optional and Serialization

`Optional` is **not serializable** by design. Using it in JPA entities or DTOs can lead to issues with frameworks like Jackson, Gson, or Hibernate. Stick to `null` in those layers and handle conversion to `Optional` at service boundaries.

## Misuse and Anti-Patterns

* Avoid calling `get()` without checking presence.
* Don’t return `Optional` for collection types (`Optional<List<T>>` is almost always wrong).
* Don’t use `Optional` for setter or constructor parameters.
* Don’t return `Optional` from a method that never returns empty.

## Comparison with Other Languages

* **Kotlin**: uses `T?` for nullable types and built-in null safety.
* **Rust**: has a built-in `Option<T>` enum with `Some` and `None`.
* **Scala**: has `Option[T]` with similar semantics.
* **JavaScript**: lacks `Optional`, but libraries (like `fp-ts`) implement similar patterns.

Java’s `Optional` is a late and limited introduction, best used in specific API return contexts.

## Best Practices Summary

* Use `Optional` for return types when absence is a valid case.
* Avoid using `Optional` for fields, parameters, or collections.
* Use `map`, `flatMap`, `filter`, and `orElseGet` instead of `isPresent`/`get`.
* Prefer `orElseThrow` to enforce required presence.
* Convert from `null`-returning libraries using `Optional.ofNullable`.
* Avoid serializing `Optional`.

## References

* [Baeldung: Guide to Java 8 Optional](https://www.baeldung.com/java-optional)
* [DZone: Understanding Java 8 Optional](https://dzone.com/articles/optional-in-java)