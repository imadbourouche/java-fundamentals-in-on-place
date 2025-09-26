# Java Basic Syntax

The **basic syntax of Java** defines the rules and structure you must follow when writing Java programs. Understanding these foundations is crucial before diving into variables, OOP, or advanced concepts.

## Structure of a Java Program

A minimal Java program has:

1. A **class** definition (in java 25 we can write without the class)
2. A **main method** (`public static void main(String[] args)`) (`void main(String[] args)` in java 25)
3. **Statements** inside the method

### Example

```java
// HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}


// JAVA 25
void main(String[] args){
    System.out.println("Hello, World!");
}
````

## Entry Point: `main` Method

* The JVM looks for a method with this exact signature:

```java
public static void main(String[] args)

// or in java 25 
void main(String[] args)
```

* **public** → accessible by JVM from outside the class
* **static** → no object required to invoke
* **void** → no return value
* **String\[] args** → command-line arguments

## Identifiers and Keywords

### Identifiers

Names for classes, methods, variables, etc. Must follow:

* Start with a **letter**, `$`, or `_`
* Cannot start with a digit
* Case-sensitive (`age` and `Age` are different)
* Should follow **camelCase** for variables/methods, **PascalCase** for classes

```java
int age = 25;
int Age = 25; // different variable
String firstName = "Imad";
class HelloWorld {}
```

### Keywords

Reserved words with special meaning (cannot be used as identifiers).
Examples: `class`, `public`, `static`, `void`, `if`, `else`, `return`, `new`.

Full list: [Java Keywords](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html)

## Statements and Blocks

* **Statement**: Single executable line (ends with `;`)

```java
int x = 10;
System.out.println(x);
```

* **Block**: Group of statements enclosed in `{ }`

```java
{
    int y = 20;
    System.out.println(y);
}
```

## Comments

Comments are ignored by the compiler.

* **Single-line**: `// text`
* **Multi-line**:

  ```java
  /* This is
     a multi-line comment */
  ```
* **Documentation (Javadoc)**:

  ```java
  /**
   * This method prints hello
   */
  ```

## Whitespace and Indentation

Whitespace is ignored by the compiler but **indentation matters for readability**.

Bad:

```java
public class Bad{public static void main(String[] args){System.out.println("Ugly");}}
```

Good:

```java
public class Good {
    public static void main(String[] args) {
        System.out.println("Readable");
    }
}
```

## Semicolons and Braces

* Every statement ends with `;`
* Blocks use `{ }`
* Method and class bodies always enclosed in `{ }`

## Putting It All Together

```java
/**
 * Demo of Java basic syntax
 */
public class SyntaxDemo {
    public static void main(String[] args) {
        // Variable declaration
        int number = 42;  

        // Print statement
        System.out.println("Number = " + number);

        // Conditional block
        if (number > 0) {
            System.out.println("Positive number");
        }
    }
}
```