# Dependency Injection (DI) in Java

Dependency Injection is a design pattern and technique that helps decouple object creation from object use, enabling cleaner, more maintainable, and testable code.

## What Is Dependency Injection?

Dependency Injection (DI) is a form of Inversion of Control (IoC) where dependencies (services) are provided to a client object by an external injector instead of being created by the client itself.

## DI Roles

- **Service**: a class offering functionality (e.g., `EmailService`).
- **Client**: a class using services.
- **Interface**: defines the service contract between client and service.
- **Injector (Assembler/Container)**: responsible for instantiating services and injecting them into clients.

## Why Use DI?

- Promotes **loose coupling** and **separation of concerns**.
- Simplifies **swap of implementations** (e.g., mock for tests, alternative strategies).
- Enhances **readability**: dependencies are explicit in interfaces.
- Improves **testability** by enabling injection of mock services.
- Reduces “dependency carrying” through layers.

## When to Use DI

DI is most beneficial when:

- A class depends on configuration data or shared services.
- Different implementations might be needed in different contexts.
- You want centralized lifecycle management (singleton, prototype).
- Loose coupling across abstraction layers is desired :contentReference[oaicite:7]{index=7}.

Avoid DI for trivial, immutable objects (like `StringBuilder`) or for dependencies that will never change.

## Types of Injection

### 1. Constructor Injection (Preferred)

Dependencies are passed via constructor, ensuring immutability and valid state:

```java
public class Client {
  private final Service service;

  public Client(Service service) {
    if (service == null) throw new IllegalArgumentException();
    this.service = service;
  }
}
````

### 2. Setter Injection

Allows optional or changeable dependencies but may risk incomplete construction:

```java
public class Client {
  private Service service;

  public void setService(Service service) {
    this.service = Objects.requireNonNull(service);
  }
}
```

### 3. Method Injection

Dependencies are used only within specific methods:

```java
public void execute(Service service) {
  Objects.requireNonNull(service).perform();
}
```

### 4. Interface Injection

Less common in Java; clients implement an injector interface:

```java
interface ServiceSetter { void setService(Service s); }

class Injector {
  void inject(ServiceSetter client) { client.setService(new Impl()); }
}
```

## DI in Practice

### Manual Wiring

Often performed in `main()` or a configuration class:

```java
public class Program {
  public static void main(String[] args) {
    Service service = new EmailService();
    Client client = new Client(service);
    client.doWork();
  }
}
```

### DI Containers

Frameworks like Spring, Guice, or simple containers let you define injection rules and manage lifecycles (singleton, prototype).

## Example: Email Service

### Legacy (Tightly Coupled)

```java
class EmailService {
  void send(String msg, String rec) { /* send email */ }
}

class App {
  private EmailService email = new EmailService();
  void process(String m, String r) { email.send(m, r); }
}
```

### Refactored with Constructor DI

```java
interface MessageService {
  void send(String message, String receiver);
}

class EmailService implements MessageService {
  public void send(String m, String r) { /* send email */ }
}

class App {
  private final MessageService svc;

  public App(MessageService svc) { this.svc = svc; }

  void process(String m, String r) {
    svc.send(m, r);
  }
}
```

Now `App` is decoupled from `EmailService` and can work with alternatives like `SmsService` or mocks.

## Advanced Concepts

### DI Containers and Lifecycle

Containers manage object creation and lifecycle (singleton, scoped, prototype). They can also invoke init or destroy callbacks.

### DI vs Factory Pattern

DI is an evolution of factory patterns and service locators—it externalizes instance creation entirely.

## Best Practices for Java Professionals

* Favor **constructor injection** for required dependencies.
* Use **interfaces** for decoupling and testability.
* Delegate wiring to a **composition root** or DI container.
* Avoid DI for parameters, fields, or data objects where unnecessary.
* Group configurations together; support multiple profiles.
* Consider **test-specific injectors** for mock configurations.
* Use container lifecycle hooks when managing external resources.


### Trade-offs

* Adds configuration complexity and indirection.
* May reduce clarity—tracing flows can require navigating container metadata.
* Containers often use reflection, which may hinder static analysis.


## Conclusion

Dependency Injection enables clean separation between *what* a class needs and *who* provides it. Adopt constructor-based DI and a composition root for robust, testable, and maintainable code. DI is powerful—but use it thoughtfully, knowing when to introduce a container and when manual wiring suffices.

## References

* [Jenkov: Dependency Injection tutorial][6]
* [Java Dependency Injection - DI Design Pattern Example Tutorial][2]
* [When to use Dependency Injection - Jenkov.com][7]
* [Is Dependency Injection Replacing the Factory Patterns? - Jenkov.com][5]

[2]: https://www.digitalocean.com/community/tutorials/java-dependency-injection-design-pattern-example-tutorial?utm_source=chatgpt.com "Java Dependency Injection - DI Design Pattern Example Tutorial"
[5]: https://jenkov.com/tutorials/dependency-injection/dependency-injection-replacing-factory-patterns.html?utm_source=chatgpt.com "Is Dependency Injection Replacing the Factory Patterns? - Jenkov.com"
[6]: https://jenkov.com/tutorials/dependency-injection/index.html?utm_source=chatgpt.com "Dependency Injection - Jenkov.com"
[7]: https://jenkov.com/tutorials/dependency-injection/when-to-use-dependency-injection.html?utm_source=chatgpt.com "When to use Dependency Injection - Jenkov.com"
