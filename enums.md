# Java Enums

Enums in Java are a special type of class used to define a fixed set of constants. They are type-safe, meaning you can only assign predefined values to an enum variable. Enums were introduced in Java 5 and are part of the `java.lang` package.

## Why Use Enums?

* Improves **readability** and **maintainability**
* Avoids the use of **magic strings** or **numbers**
* Ensures **compile-time type safety**
* Can include **fields, methods, and constructors**


## Basic Syntax

```java
enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

Usage:

```java
Direction dir = Direction.NORTH;
```


## Behind the Scenes

Each enum constant is actually an instance of the enum type. The above `Direction` enum compiles into a class like:

```java
final class Direction extends Enum<Direction> {
    public static final Direction NORTH = new Direction("NORTH", 0);
    public static final Direction SOUTH = new Direction("SOUTH", 1);
    // ...
}
```


## Enums in Switch Statements

```java
switch (dir) {
    case NORTH -> System.out.println("Going north");
    case SOUTH -> System.out.println("Going south");
    default -> System.out.println("Other direction");
}
```

## Adding Fields and Methods

```java
enum Status {
    SUCCESS(200),
    NOT_FOUND(404),
    SERVER_ERROR(500);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
```

Usage:

```java
System.out.println(Status.SUCCESS.getCode());
```


## Enum Methods

All enums inherit methods from the `Enum` class:

| Method      | Description                          |
| ----------- | ------------------------------------ |
| `values()`  | Returns an array of enum constants   |
| `valueOf()` | Returns the enum constant by name    |
| `ordinal()` | Returns the position of the constant |
| `name()`    | Returns the name of the constant     |
| `toString()`| returns a string value of the name of the given enum instance  |

Example:

```java
for (Status s : Status.values()) {
    System.out.println(s + ": " + s.getCode());
}
/*
SUCCESS: 200
NOT_FOUND: 404
SERVER_ERROR: 500
*/
```


## Enum with Abstract Methods
It is possible for a Java enum class to have abstract methods too. If an enum class has an abstract method, then each instance of the enum class must implement it.

```java
enum Operation {
    ADD {
        public int apply(int a, int b) { return a + b; }
    },
    MULTIPLY {
        public int apply(int a, int b) { return a * b; }
    };

    public abstract int apply(int a, int b);
}

public class Main {
    public static void main(String[] args) {
        int result1 = Operation.ADD.apply(3, 4);       // 3 + 4 = 7
        int result2 = Operation.MULTIPLY.apply(3, 4);  // 3 * 4 = 12
    }
}
```

## EnumSet and EnumMap

* **EnumSet**: High-performance Set implementation for use with enum types
* **EnumMap**: Map implementation for enum keys

```java
EnumSet<Direction> directions = EnumSet.of(Direction.NORTH, Direction.EAST);
EnumMap<Status, String> statusMessages = new EnumMap<>(Status.class);
statusMessages.put(Status.SUCCESS, "Request succeeded");
```

## Best Practices

* Use enums when a variable can only have a **set of predefined values**
* Keep enums **immutable**
* Avoid storing business logic in enums unless it's tightly coupled
* Prefer enums over `int` or `String` constants
