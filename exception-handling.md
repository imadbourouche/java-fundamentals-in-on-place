# Java Exceptions – Full Guide

## 📘 What is an Exception?

An **Exception** in Java is an **unwanted or unexpected event** that disrupts the normal flow of the program during execution.

> All exceptions are objects in Java, instances of classes derived from `java.lang.Throwable`.


## 🧭 Throwable Hierarchy

```
java.lang.Object
 └── java.lang.Throwable
      ├── java.lang.Error         // Serious issues, usually beyond control (e.g., OutOfMemoryError)
      └── java.lang.Exception     // Recoverable conditions the program should catch
           └── java.lang.RuntimeException
```


## 🎯 Types of Exceptions

### 1. **Checked Exceptions**

* Checked at compile-time.
* Must be either **caught** or **declared** using `throws`.
* Examples:

  * `IOException`
  * `SQLException`
  * `FileNotFoundException`

### 2. **Unchecked Exceptions**

* Checked at runtime.
* Typically due to programming bugs.
* Subclasses of `RuntimeException`.
* Examples:

  * `NullPointerException`
  * `ArrayIndexOutOfBoundsException`
  * `ArithmeticException`


## 🧪 Common Java Exceptions

| Exception                        | Type      | Cause                                  |
| -------------------------------- | --------- | -------------------------------------- |
| `NullPointerException`           | Unchecked | Accessing object with `null` reference |
| `ArrayIndexOutOfBoundsException` | Unchecked | Invalid array index                    |
| `ClassNotFoundException`         | Checked   | Class not found during loading         |
| `FileNotFoundException`          | Checked   | File is missing                        |
| `IOException`                    | Checked   | Input/Output operation failed          |
| `ArithmeticException`            | Unchecked | Division by zero                       |
| `NumberFormatException`          | Unchecked | Invalid number format conversion       |
| `SQLException`                   | Checked   | SQL operation failed                   |


## 🛠️ Exception Handling Keywords

### 1. `try`

Wraps code that might throw an exception.

### 2. `catch`

Handles the exception type(s) thrown in `try`.

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero!");
}
```

### 3. `finally`

Block that **always executes**, regardless of exception.

```java
finally {
    System.out.println("Cleaning up...");
}
```

### 4. `throw`

Used to explicitly **throw an exception**.

```java
throw new IllegalArgumentException("Invalid argument");
```

### 5. `throws`

Used in method signature to **declare** exceptions that can be thrown.

```java
public void readFile() throws IOException {
    // logic
}
```

## 🎨 Custom Exceptions

You can create your own exception class by extending `Exception` or `RuntimeException`.

```java
class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}
```

Usage:

```java
throw new MyException("Custom error occurred!");
```


## 🔁 Catching Multiple Exceptions

### Option 1: Multiple `catch` blocks

```java
try {
    // some code
} catch (IOException e) {
    // handle IO
} catch (SQLException e) {
    // handle SQL
}
```

### Option 2: Multi-catch block (Java 7+)

```java
catch (IOException | SQLException e) {
    e.printStackTrace();
}
```


## 🧱 Best Practices

* Don’t catch `Exception` or `Throwable` unless absolutely necessary.
* Use specific exception types.
* Don’t suppress exceptions (e.g., empty catch blocks).
* Always clean up with `finally` or use try-with-resources.
* Use custom exceptions for domain-specific errors.


## 🔧 Try-with-Resources (Java 7+)

- Automatically closes resources that implement `AutoCloseable`.

- **Try-with-resources** is a feature in Java 7+ that **automatically closes resources** like files, streams, sockets, etc., after use — **without needing a `finally` block**.

> What is a Resource?
> 
> A **resource** is any object that must be **closed after its use**.
To be used in try-with-resources, it must implement the `AutoCloseable` (or `Closeable`) interface.
> 
> Examples:
> * `FileInputStream`, `BufferedReader`, `Scanner`
> * `Connection`, `PreparedStatement` (JDBC)


```java
try (ResourceType name = new ResourceType()) {
    // use the resource
} catch (Exception e) {
    // handle exception
}
```


### 📦 Example: Reading a File

```java
import java.io.*;

// With Try-With-Resources (Manual Closing)
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
    System.out.println(line);
} catch (IOException e) {
    e.printStackTrace();
}
// br is automatically closed here

// ----------------- //

// Without Try-With-Resources (Manual Closing)
BufferedReader br = null;
try {
    br = new BufferedReader(new FileReader("file.txt"));
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        if (br != null) br.close(); // manual close
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Multiple Resources
try (
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    FileWriter fw = new FileWriter("output.txt")
) {
    String line = br.readLine();
    fw.write(line);
} catch (IOException e) {
    System.out.println("I/O Error: " + e.getMessage());
}
```

## 📚 Conclusion

Java provides a robust and flexible mechanism for handling both expected and unexpected events during program execution. Mastering exceptions helps create clean, resilient, and maintainable applications.
