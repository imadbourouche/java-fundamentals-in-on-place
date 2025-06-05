## Method Chaining in Java

**Method chaining** is a programming technique where multiple method calls are linked together in a single statement. Each method returns an object that allows the next method call, enabling a fluent and readable coding style.

### Why use Method Chaining?

- Improves code readability
- Reduces temporary variables
- Supports fluent APIs and builder patterns

---

## How It Works

Each method in the chain returns `this` or another object that supports chaining.

```java
object.method1().method2().method3();
````

---

## Example 1: Basic Method Chaining

```java
class Person {
    private String name;
    private int age;

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public void show() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        p.setName("Imad").setAge(28).show();
    }
}
```

---

## Example 2: Chaining with Different Objects

```java
class Engine {
    public Engine start() {
        System.out.println("Engine started");
        return this;
    }
}

class Car {
    private Engine engine = new Engine();

    public Car wash() {
        System.out.println("Car washed");
        return this;
    }

    public Engine getEngine() {
        return engine;
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.wash().getEngine().start();
    }
}
```

---

## Example 3: Real-world Example with StringBuilder

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello").append(" ").append("World!");
System.out.println(sb.toString());  // Output: Hello World!
```

---

## Example 4: Fluent Builder Pattern

```java
class User {
    private String name;
    private String email;

    public static class Builder {
        private User user = new User();

        public Builder name(String name) {
            user.name = name;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public User build() {
            return user;
        }
    }

    public void display() {
        System.out.println("Name: " + name + ", Email: " + email);
    }
}

public class Main {
    public static void main(String[] args) {
        User u = new User.Builder()
                     .name("Imad")
                     .email("imad@example.com")
                     .build();
        u.display();
    }
}
```

---

## Benefits of Method Chaining

* Cleaner and more readable code
* Facilitates building complex objects with a fluent API
* Reduces boilerplate code

---

## Things to Keep in Mind

* Avoid overly long chains to maintain readability
* Handle exceptions properly if methods can fail
* Immutable objects require returning new instances on each method call

---
