# Variables and Data Types in Java

Variables are **named storage locations** used to hold data.  
In Java, every variable has:
- A **type** (primitive or reference)
- A **name** (identifier)
- A **value**

## Declaring Variables

```java
int age;             // declaration
age = 25;            // initialization
int year = 2025;     // declaration + initialization
````

Rules for identifiers:

* Start with letter, `_`, or `$`
* Case-sensitive
* Cannot use reserved keywords (`int`, `class`, `for`, etc.)

## Types of Variables

1. **Local Variables** → declared inside methods, exist during method execution only.
2. **Instance Variables** → defined inside class, but outside methods. Each object gets its own copy.
3. **Static Variables** → shared by all objects of a class (declared with `static`).

### Example

```java
public class Person {
    String name;          // instance variable
    static String species = "Human"; // static variable

    public void introduce() {
        int age = 30;     // local variable
        System.out.println("Hi, I'm " + name + ", " + age + " years old.");
    }
}
```

## Data Types in Java

Java is **strongly typed** → every variable must have a type.

### 1. Primitive Data Types

| Type      | Size            | Default  | Example                |
| --------- | --------------- | -------- | ---------------------- |
| `byte`    | 8-bit           | 0        | `byte b = 100;`        |
| `short`   | 16-bit          | 0        | `short s = 2000;`      |
| `int`     | 32-bit          | 0        | `int i = 100000;`      |
| `long`    | 64-bit          | 0L       | `long l = 100000L;`    |
| `float`   | 32-bit          | 0.0f     | `float f = 3.14f;`     |
| `double`  | 64-bit          | 0.0d     | `double d = 2.718;`    |
| `char`    | 16-bit          | '\u0000' | `char c = 'A';`        |
| `boolean` | 1-bit (logical) | false    | `boolean flag = true;` |

### 2. Reference Data Types

* Store **addresses** (references) to objects.
* Examples: `String`, arrays, custom classes objects.

```java
String name = "Imad";   // String object reference
int[] numbers = {1, 2, 3}; // Array reference
Person p = new Person();   // Custom class object reference
```

## Default Values

* Local variables → **must be initialized** before use.
* Instance/static variables → get default values:

| Type       | Default Value |
| ---------- | ------------- |
| numeric    | 0             |
| boolean    | false         |
| char       | '\u0000'      |
| object ref | null          |

## Type Casting

### Implicit Casting (Widening)

```java
int x = 100;
double y = x;  // int → double
```

### Explicit Casting (Narrowing)

```java
double a = 9.8;
int b = (int) a;  // truncates to 9
```

## Wrapper Classes

Java provides object representations of primitives:

* `int` → `Integer`
* `double` → `Double`
* `boolean` → `Boolean`

### Autoboxing / Unboxing

```java
Integer obj = 10;    // autoboxing (int → Integer)
int num = obj;       // unboxing (Integer → int)
```

Useful in **collections** (`List<Integer>` cannot hold primitive `int`).

## `var` Keyword (Java 10+)

* Allows local variable type inference.
* The compiler infers type from initialization.

```java
var message = "Hello";   // inferred as String
var number = 123;        // inferred as int
```

> Restrictions:
> * Only for **local variables** with initializer.
> * Not allowed for method parameters, return types, or fields.

## Example Program

```java
public class DataTypesDemo {
    int instanceVar = 42;       // instance variable
    static String staticVar = "Shared"; // static variable

    public static void main(String[] args) {
        // Local variables
        int age = 25;
        double salary = 4500.75;
        boolean isActive = true;

        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Active: " + isActive);

        // Reference types
        String name = "Imad";
        int[] scores = {90, 85, 92};

        System.out.println("Name: " + name);
        System.out.println("First score: " + scores[0]);
    }
}
```
