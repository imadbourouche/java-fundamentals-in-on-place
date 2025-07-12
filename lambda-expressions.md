# Java Lambda Expressions

This repository is a hands-on guide to understanding and using **Lambda Expressions** in Java. Lambda expressions were introduced in Java 8 as part of the move toward functional programming. 

## What is a Lambda Expression?

A Lambda Expression is essentially an **anonymous function** that you can pass around. It allows you to write methods inline, making code more concise and functional.

```java
// lambda expression syntax
(parameters) -> expression
(parameters) -> { statements }
````

## Functional Interfaces

A lambda expression can only be assigned to a variable whose type is a **functional interface** which is an interface with exactly one abstract method.

### Example: Custom Functional Interface

```java
@FunctionalInterface
interface MyFunction {
    void apply();
}

MyFunction f = () -> System.out.println("Hello from Lambda");
f.apply();
```

Common built-in functional interfaces (from `java.util.function` package):

* `Function<T, R>`
* `Predicate<T>`
* `Consumer<T>`
* `Supplier<T>`
* `BiFunction<T, U, R>`


## Syntax Breakdown

### 1. No Parameters

```java
() -> System.out.println("Hello")
```

### 2. One Parameter (type inferred)

```java
x -> x * x
```

### 3. Multiple Parameters

```java
(x, y) -> x + y
```

### 4. Block Body

```java
(x, y) -> {
    int sum = x + y;
    return sum;
}
```


## Type Inference

Java can often infer parameter types (means that the compiler automatically determines a type) based on the context. You can still specify types explicitly:

```java
(String s) -> s.length()
```

or simply:

```java
s -> s.length()
```


## Variable Scope in Lambdas

* Can access:
   * Local variables
   * Instance variables
   * Static variables

```java
// local variable
int x = 10;
Runnable r = () -> System.out.println(x); // OK

// instance variable
public class EventConsumerImpl {

    private String name = "MyConsumer";

    public void attach(MyEventProducer eventProducer){
        eventProducer.listen(e -> {
            System.out.println(this.name);
        });
    }
}

// static variable
public class EventConsumerImpl {
    private static String someStaticVar = "Some text";

    public void attach(MyEventProducer eventProducer){
        eventProducer.listen(e -> {
            System.out.println(someStaticVar);
        });
    }
}
```


## Using Lambdas with Threads

```java
Thread thread = new Thread(() -> {
    System.out.println("Running in a lambda-based thread");
});
thread.start();
```

---

## Lambdas in Collections

Sorting with comparator:
   - sort method accepts a List<T> and a Comparator<? super T>: `static <T> void sort(List<T> list, Comparator<? super T> c)`
   - `(a, b) -> a.compareToIgnoreCase(b)` is a lambda expression.
   - It matches the Comparator<String> interface, whose method is: int compare(String a, String b);
```java
Collections.sort(names, (a, b) -> a.compareToIgnoreCase(b));
```


```java
List<String> names = Arrays.asList("Imad", "boualam", "yacine");
names.forEach(name -> System.out.println(name));
// forEach is a method from the Iterable<T> interface. It accepts a Consumer<T> as its parameter.
// void forEach(Consumer<? super T> action)
```

## Method References

Shorter form of lambda when calling existing methods.

```java
names.forEach(System.out::println);
```

Equivalent to:

```java
names.forEach(name -> System.out.println(name));
```


## Why Use Lambda Expressions?

* Cleaner and more readable code
* Eliminates boilerplate (especially anonymous classes)
* Enables functional programming with streams and collections
* Encourages immutability and declarative style

## Sources

- [Jenkov’s Lambda Expressions tutorial](https://jenkov.com/tutorials/java/lambda-expressions.html).