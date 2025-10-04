# Methods in Java

Methods in Java are **blocks of code that perform specific tasks**. They promote **code reusability**, **modularity**, and **abstraction**.

## 1. Defining a Method

General syntax:

```java
modifier returnType methodName(parameters) {
    // method body
    return value; // if returnType is not void
}
````

* **modifier**: `public`, `private`, `protected`, `default`, `static`, `final`, `abstract`, `synchronized`, `native`, `strictfp`.
* **returnType**: the type of value returned (`int`, `void`, `String`, etc.)
* **methodName**: must follow Java naming conventions (camelCase).
* **parameters**: optional, zero or more arguments.
* **method body**: contains executable statements.

## 2. Calling a Method

```java
ClassName obj = new ClassName();
int sum = obj.add(5, 3);
```

Static methods can be called **without creating an object**:

```java
int result = Math.max(10, 20); // directly using class name
```

## 3. Method Parameters

### Pass by Value (Java Only)

Java is strictly **pass-by-value**. For objects, the **reference itself is passed by value**, not the object.

```java
void changeValue(int x) {
    x = 50;
}

int num = 10;
changeValue(num);
System.out.println(num); // still 10
```

For objects:

```java
void changeName(Person p) {
    p.name = "Alice";
}

Person person = new Person("Bob");
changeName(person);
System.out.println(person.name); // Alice (reference points to same object)
```

### Varargs (Variable Arguments)

Allows passing multiple arguments of the same type.

```java
int sum(int... numbers) {
    int result = 0;
    for (int n : numbers) result += n;
    return result;
}
```

Usage:

```java
System.out.println(sum(1, 2, 3, 4)); // 10
```

## 4. Method Overloading

Multiple methods can have the **same name** but different **parameter lists**.

```java
int multiply(int a, int b) {
    return a * b;
}

double multiply(double a, double b) {
    return a * b;
}
```

> Note: Overloading is resolved **at compile-time** (static polymorphism).

## 5. Method Overriding

A subclass can **provide a new implementation** of a method defined in its superclass.

```java
class Animal {
    void sound() { System.out.println("Animal sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Bark"); }
}
```

> Note: Overriding is resolved **at runtime** (dynamic polymorphism).

## 6. Access Modifiers

* **public** → accessible from everywhere.
* **protected** → accessible in the same package + subclasses.
* **default (package-private, no modifier)** → accessible only in the same package.
* **private** → accessible only inside the same class.

## 7. Non-Access Modifiers for Methods

* **static** → belongs to the class, not the instance.
* **final** → cannot be overridden by subclasses.
* **abstract** → no body; must be implemented by subclass.
* **synchronized** → only one thread can access the method at a time.
* **native** → Declared in Java and implemented in another language (C/C++) via JNI.
* **strictfp** → forces floating-point calculations inside the method to follow IEEE 754 standard (deterministic results across platforms).

## 8. Return Types

* **Primitive return types**: return basic values (`int`, `double`, etc.).
* **Object return types**: return objects.
* **Void**: no return value.