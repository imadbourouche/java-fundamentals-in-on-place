# Static vs Dynamic Binding

## 🔗 What is Binding?

**Binding** refers to the process of associating a method call with its corresponding method body. In Java, this association can occur at two different times:

- **Static Binding (Early Binding):** Occurs at compile time.
- **Dynamic Binding (Late Binding):** Occurs at runtime.


## ⚡ Static Binding (Early Binding)

### ✅ Definition

Static Binding happens at **compile time**. The compiler determines the method to invoke based on the reference type and method signature.

### 🧠 When Does Static Binding Occur?

- **Method Overloading**
- **Static Methods**
- **Final Methods**
- **Private Methods**

### 📌 Key Characteristics

- Faster execution due to compile-time resolution.
- Does **not** support polymorphism.
- Determined by **reference type**, not object type.

### 📦 Example: Method Overloading

```java
class Printer {
    void print(int a) {
        System.out.println("Integer: " + a);
    }

    void print(String s) {
        System.out.println("String: " + s);
    }
}

public class Main {
    public static void main(String[] args) {
        Printer p = new Printer();
        p.print(10);           // Integer: 10
        p.print("Hello");      // String: Hello
    }
}
````

In this example, the method to invoke is determined at compile time based on the method signature, exemplifying **Static Binding**.

---

## 🔄 Dynamic Binding (Late Binding)

### ✅ Definition

Dynamic Binding occurs at **runtime**. The JVM determines the method to invoke based on the **actual object**, not the reference type.

### 🧠 When Does Dynamic Binding Occur?

* **Method Overriding**
* With **non-static**, **non-final**, and **non-private** instance methods.

### 📌 Key Characteristics

* Supports **runtime polymorphism**.
* Slightly slower due to runtime resolution.
* Determined by **object type**, not reference type.

### 📦 Example: Method Overriding

```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

public class BindingExample {
    public static void main(String[] args) {
        Animal animal = new Animal();       // Static Binding
        animal.sound();                     // Output: Animal makes a sound

        Animal animalDog = new Dog();       // Dynamic Binding
        animalDog.sound();                  // Output: Dog barks
    }
}
```

Here, `animal.sound()` is resolved at compile time, while `animalDog.sound()` is resolved at runtime based on the actual object type, demonstrating **Dynamic Binding**.

---

## 🎯 Mixed Example: Overloading and Overriding

```java
class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }

    void sound(String text) { // Method overloading
        System.out.println(text);
    }
}

class Boldog extends Dog {
    @Override
    void sound(String text) {
        System.out.println("Boldog says: " + text);
    }
}
```

### **1. Static Binding (Compile-Time)**  
> - Resolved **at compile time** based on **reference type**.  
> - Applies to:  
>    - **Method overloading**  
>    - **`private`, `final`, `static` methods**  
>    - **Direct calls (no polymorphism)**  

#### **Examples**  
```java
Animal animal = new Animal();
animal.sound(); // Calls Animal.sound() (Static)

Dog dog = new Dog();
dog.sound("Woof!"); // Calls Dog.sound(String) (Static - Overloading)

Boldog boldog = new Boldog();
boldog.sound("Bark!"); // Calls Boldog.sound(String) (Static)
```

### **2. Dynamic Binding (Runtime)**  
> - Resolved **at runtime** based on **actual object type**.  
> - Applies to:  
>    - **Method overriding (polymorphism)**  

#### **Examples**  
```java
Animal animalDog = new Dog();
animalDog.sound(); // Calls Dog.sound() (Dynamic)

Dog dogBoldog = new Boldog();
dogBoldog.sound("Grr!"); // Calls Boldog.sound(String) (Dynamic)

Animal boldogAsAnimal = new Boldog();
boldogAsAnimal.sound(); // Calls Dog.sound() (Dynamic - inherited)
```


## 🧠 Additional notes

* **Constructors** are always statically bound.
* **Static methods** are bound using the **reference type**, even if overridden.
* Overloading is resolved at compile time, while overriding is resolved at runtime.
